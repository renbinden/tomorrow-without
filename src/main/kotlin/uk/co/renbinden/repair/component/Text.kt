package uk.co.renbinden.repair.component

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Text(val text: String) : Component {
    companion object : ComponentMapper<Text>(Text::class)
}