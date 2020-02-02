package uk.co.renbinden.repair.component

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class WaitTimes(val waitTimes: Map<Int, Double>) : Component {
    companion object : ComponentMapper<WaitTimes>(WaitTimes::class)
}