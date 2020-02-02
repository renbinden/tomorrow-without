package uk.co.renbinden.repair.renderer

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.repair.component.Depth
import uk.co.renbinden.repair.component.Image
import uk.co.renbinden.repair.component.Position
import uk.co.renbinden.repair.component.HorizontalWrap

class ImageRenderer(val canvas: HTMLCanvasElement, val ctx: CanvasRenderingContext2D, val engine: Engine) : Renderer {
    override fun onRender(dt: Double) {
        engine.entities
            .filter { it.has(Image) && it.has(Position) && it.has(Depth) }
            .sortedByDescending { entity -> entity[Depth].depth }
            .forEach { entity ->
                val image = entity[Image]
                val position = entity[Position]
                if (image.image.isLoaded) {
                    if (entity.has(HorizontalWrap)) {
                        ctx.drawImage(image.image.image, position.x % canvas.width, position.y)
                        if (position.x % canvas.width < 0.0) {
                            ctx.drawImage(image.image.image, (position.x % canvas.width) + image.image.image.width - 1.0, position.y)
                        } else if (position.x + image.image.image.width % canvas.width > canvas.width) {
                            ctx.drawImage(image.image.image, (position.x % canvas.width) - image.image.image.width + 1.0, position.y)
                        }
                    } else {
                        ctx.drawImage(image.image.image, position.x, position.y)
                    }
                }
            }
    }
}