package uk.co.renbinden.repair.component

import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Portrait(val portrait: ImageAsset) : Component {
    companion object : ComponentMapper<Portrait>(Portrait::class)
}