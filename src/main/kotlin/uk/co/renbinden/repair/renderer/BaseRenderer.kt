package uk.co.renbinden.repair.renderer

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

class BaseRenderer(val canvas: HTMLCanvasElement, val ctx: CanvasRenderingContext2D) : Renderer {
    override fun onRender(dt: Double) {
        ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
        ctx.fillStyle = "#000000"
        ctx.fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
    }
}