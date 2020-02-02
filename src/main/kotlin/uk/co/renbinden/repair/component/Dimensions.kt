package uk.co.renbinden.repair.component

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Dimensions(val width: Double, val height: Double) : Component {
    companion object : ComponentMapper<Dimensions>(Dimensions::class)
}