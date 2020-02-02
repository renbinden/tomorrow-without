package uk.co.renbinden.repair.component

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

class Acceleration(val ddx: Double, val ddy: Double) : Component {
    companion object : ComponentMapper<Acceleration>(Acceleration::class)
}