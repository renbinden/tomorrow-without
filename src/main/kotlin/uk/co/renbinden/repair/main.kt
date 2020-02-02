package uk.co.renbinden.repair

import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.repair.assets.Assets
import uk.co.renbinden.repair.screen.PlayScreen

fun main() {
    val app = App()
    val assets = Assets()
    app.screen = PlayScreen(app, assets)
}