package uk.co.renbinden.repair.assets

import org.w3c.dom.Audio
import uk.co.renbinden.ilse.asset.ImageAsset

class Assets {

    inner class Images {
        val bgClouds = ImageAsset("static/images/bg_clouds.png")
        val bgGround = ImageAsset("static/images/bg_ground.png")
        val bgHills = ImageAsset("static/images/bg_hills.png")
        val bgSky = ImageAsset("static/images/bg_sky.png")
        val anna = ImageAsset("static/images/char1.png")
        val evelyn = ImageAsset("static/images/char2.png")
        val annaPortrait = ImageAsset("static/images/portrait_char_1.png")
        val evelynPortrait = ImageAsset("static/images/portrait_char_2.png")
    }

    inner class Music {
        val music = Audio("static/music/music.ogg")
        init {
            music.loop = true
        }
    }

    val images = Images()
    val music = Music()

}