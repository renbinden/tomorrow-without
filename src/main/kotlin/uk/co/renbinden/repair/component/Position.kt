package uk.co.renbinden.repair.component

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Position(var x: Double, var y: Double) : Component {
    companion object : ComponentMapper<Position>(Position::class)
}