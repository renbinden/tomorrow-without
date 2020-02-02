package uk.co.renbinden.repair.component

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Depth(val depth: Int) : Component {
    companion object : ComponentMapper<Depth>(Depth::class)
}