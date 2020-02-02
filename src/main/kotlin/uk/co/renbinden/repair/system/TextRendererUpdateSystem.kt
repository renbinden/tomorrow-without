package uk.co.renbinden.repair.system

import uk.co.renbinden.ilse.ecs.system.System
import uk.co.renbinden.repair.renderer.TextRenderer

class TextRendererUpdateSystem(val textRenderer: TextRenderer) : System() {

    override fun onTick(dt: Double) {
        textRenderer.onTick(dt)
    }

}