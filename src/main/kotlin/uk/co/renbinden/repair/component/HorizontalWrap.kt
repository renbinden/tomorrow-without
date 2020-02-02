package uk.co.renbinden.repair.component

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

class HorizontalWrap : Component {
    companion object : ComponentMapper<HorizontalWrap>(HorizontalWrap::class)
}