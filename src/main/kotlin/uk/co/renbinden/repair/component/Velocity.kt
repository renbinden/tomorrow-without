package uk.co.renbinden.repair.component

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Velocity(var dx: Double, var dy: Double) : Component {
    companion object : ComponentMapper<Velocity>(Velocity::class)
}