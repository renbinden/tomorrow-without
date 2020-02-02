package uk.co.renbinden.repair.screen

import org.w3c.dom.*
import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.ilse.app.screen.Screen
import uk.co.renbinden.ilse.ecs.engine
import uk.co.renbinden.ilse.event.Events
import uk.co.renbinden.ilse.event.Listener
import uk.co.renbinden.ilse.input.event.MouseDownEvent
import uk.co.renbinden.repair.assets.Assets
import kotlin.browser.document

class PlayScreen(val app: App, val assets: Assets) : Screen(engine {

}) {

    val canvas = document.getElementById("game") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D
    val mouseDownListener = Listener<MouseDownEvent>({ event -> onMouseClick(event) })

    init {
        Events.addListener(MouseDownEvent, mouseDownListener)
    }

    private fun onMouseClick(event: MouseDownEvent) {
        Events.removeListener(MouseDownEvent, mouseDownListener)
        app.screen = MainScreen(app, assets)
    }

    override fun onRender(dt: Double) {
        ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
        ctx.fillStyle = "#000000"
        ctx.fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
        ctx.fillStyle = "#ffffff"
        ctx.font = "20px 'Merriweather', serif"
        ctx.textBaseline = CanvasTextBaseline.MIDDLE
        ctx.textAlign = CanvasTextAlign.CENTER
        ctx.fillText("Click to start", canvas.width / 2.0, canvas.height / 2.0)
    }
}