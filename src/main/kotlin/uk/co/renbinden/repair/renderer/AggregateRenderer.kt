package uk.co.renbinden.repair.renderer

class AggregateRenderer(vararg val renderers: Renderer) : Renderer {
    override fun onRender(dt: Double) = renderers.forEach { renderer -> renderer.onRender(dt) }
}