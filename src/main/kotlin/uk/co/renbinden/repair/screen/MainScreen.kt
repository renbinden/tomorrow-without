package uk.co.renbinden.repair.screen

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.ilse.app.screen.Screen
import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.ecs.engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.ilse.event.Events
import uk.co.renbinden.ilse.event.Listener
import uk.co.renbinden.ilse.input.event.MouseDownEvent
import uk.co.renbinden.repair.assets.Assets
import uk.co.renbinden.repair.component.*
import uk.co.renbinden.repair.event.TextCloseEvent
import uk.co.renbinden.repair.renderer.AggregateRenderer
import uk.co.renbinden.repair.renderer.BaseRenderer
import uk.co.renbinden.repair.renderer.ImageRenderer
import uk.co.renbinden.repair.renderer.TextRenderer
import uk.co.renbinden.repair.system.AccelerationSystem
import uk.co.renbinden.repair.system.CollisionSystem
import uk.co.renbinden.repair.system.TextRendererUpdateSystem
import uk.co.renbinden.repair.system.VelocitySystem
import kotlin.browser.document

class MainScreen(val app: App, val assets: Assets) : Screen(
    engine {
        add(VelocitySystem())
        add(AccelerationSystem())
        add(CollisionSystem())

        add(entity {
            add(Image(assets.images.bgSky))
            add(Depth(3))
            add(Position(0.0, 0.0))
        })

        add(entity {
            add(Image(assets.images.bgClouds))
            add(Depth(2))
            add(Position(0.0, 0.0))
            add(Velocity(-4.0, 0.0))
            add(HorizontalWrap())
        })

        add(entity {
            add(Image(assets.images.bgHills))
            add(Depth(1))
            add(Position(0.0, 0.0))
        })

        add(entity {
            add(Image(assets.images.bgGround))
            add(Depth(0))
            add(Position(0.0, 0.0))
        })

        add(entity {
            add(Image(assets.images.anna))
            add(Depth(-1))
            add(Position(320.0, 368.0))
        })

        add(entity {
            add(Image(assets.images.evelyn))
            add(Depth(-1))
            add(Position(336.0, 368.0))
        })
    }
) {

    val canvas = document.getElementById("game") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    val mouseClickListener = Listener<MouseDownEvent>({ onMouseClick() })
    val textCloseListener = Listener<TextCloseEvent>({ onTextClose() })

    val textRenderer = TextRenderer(canvas, ctx, engine)

    var storyProgress = 0

    val story = arrayOf(
        { message("Hey,(wait 0.5) Evelyn?", assets.images.annaPortrait) },
        { message("Mm?", assets.images.evelynPortrait) },
        { message(".(wait 0.5).(wait 0.5).(wait 0.5)Thank you.", assets.images.annaPortrait) },
        { message("Hm?(wait 0.5) For what?", assets.images.evelynPortrait) },
        { message("For this.(wait 0.5) For all of this.(wait 0.5)\n" +
                "You were there at exactly the right time and knew exactly what would help.\n" +
                "And now we're here, (wait 0.5)looking up at the stars, (wait 0.5)several hundred miles from home...", assets.images.annaPortrait) },
        { message(".(wait 0.5).(wait 0.5).(wait 0.5)Yeah. It's amazing how such a small world can be so (wait 0.5)big,(wait 0.5) sometimes.", assets.images.evelynPortrait) },
        { message("I'm so glad we got to be friends.", assets.images.annaPortrait) },
        { message("(wait 0.5)Me too(wait 0.5).(wait 0.5).(wait 0.5).(wait 1.0)\n" +
                "Hey,(wait 0.5) Anna?(wait 0.5)\n" +
                "Have you been(wait 1.0) feeling alright?(wait 1.0) You know,(wait 0.5) since(wait 0.5).(wait 0.5).(wait 0.5).", assets.images.evelynPortrait) },
        { message("(sigh) (wait 1.0)You know the answer to that,(wait 0.5) already.", assets.images.annaPortrait) },
        { message("...(wait 0.5)Yeah.(wait 1.0) I guess.", assets.images.evelynPortrait) },
        { message("She meant the world to me.(wait 1.0)\n" +
                "And for it to all just(wait 0.5).(wait 0.5).(wait 0.5). end?(wait 1.0)\n" +
                "That suddenly?(wait 0.5)\n" +
                "It feels as though a thousand emotions are all crashing in on me all at once.", assets.images.annaPortrait) },
        { message("I'd say I understand how you feel,(wait 0.5) but you never really can,(wait 0.5) can you?", assets.images.evelynPortrait) },
        { message("No;(wait 0.7) I daresay you can't.(wait 1.0)\n" +
                "Still(wait 0.5).(wait 0.5).(wait 0.5).(wait 0.5) I get the sentiment,(wait 0.5) and I appreciate you being here.(wait 1.0)\n" +
                "It's like(wait 0.5).(wait 0.5).(wait 0.5). there were so many things still to do together,(wait 0.5) you know?(wait 1.0)\n" +
                "So many things(wait 0.5) that we'd started,(wait 0.5) that never found an end.", assets.images.annaPortrait) },
        { message("I guess(wait 1.0) life is just like that,(wait 0.5) sometimes.\n" +
                "Nothing ever really comes to a neat conclusion,(wait 0.5) and in the end it's going to be messy.\n" +
                "Everything is left half-done,(wait 0.5) and all you can really do(wait 0.5) is keep picking yourself back up,(wait 0.5) and putting yourself back together.", assets.images.evelynPortrait) },
        { message(".(wait 0.5).(wait 0.5).(wait 0.5)Yeah.", assets.images.annaPortrait) },
        { message("Say,(wait 0.5) you want to go climb that one tomorrow?", assets.images.evelynPortrait) },
        { message("I(wait 0.5).(wait 0.5).(wait 0.5).(wait 0.5) don't think I can do that one yet.(wait 1.0) That was the first one we did together.", assets.images.annaPortrait) },
        { message("Okay(wait 0.5).(wait 0.5).(wait 0.5).(wait 0.5)\n" +
                "Something new,(wait 0.5) then.", assets.images.evelynPortrait) },
        { message("Yeah.", assets.images.annaPortrait) },
        { message("That one?", assets.images.evelynPortrait) },
        { message("Yeah.(wait 0.5)\n" +
                "That sounds nice.(wait 2.0)\n" +
                "Polaris is quite clear,(wait 0.5) tonight.", assets.images.annaPortrait) },
        { message("Wow, yes.(wait 0.5) Not too many clouds.(wait 0.5) The whole of Ursa Minor is visible.", assets.images.evelynPortrait) },
        { message("Yeah.(wait 0.5).(wait 0.5).(wait 1.5)\n" +
                "It's going to be tough, huh?", assets.images.annaPortrait) },
        { message(".(wait 0.5).(wait 0.5).(wait 0.5)yeah.\n" +
                "I feel like(wait 0.5) you need to find(wait 0.5) a part of yourself again.\n" +
                "Like, when someone's that important to you,(wait 0.5) you really start to form a part of each other.\n" +
                "And when they're gone,(wait 1.0) you have to rebuild a new part of yourself,(wait 0.5) before you can feel whole again.\n" +
                "And create your own serenity from within.", assets.images.evelynPortrait) },
        { message(".(wait 0.5).(wait 0.5).(wait 0.5)Perhaps.(wait 1.0)\n" +
                "Hey,(wait 0.5) Evelyn?", assets.images.annaPortrait) },
        { message("Hm?", assets.images.evelynPortrait) },
        { message("I'm really looking forward to tomorrow.", assets.images.annaPortrait) },
        { message(".(wait 0.5).(wait 0.5).(wait 0.5)Me,(wait 0.5) too.", assets.images.evelynPortrait) },
        { message("Evelyn?", assets.images.annaPortrait) },
        { message ("Yes?", assets.images.evelynPortrait) },
        { message(".(wait 0.5).(wait 0.5).(wait 0.5)Thank you.", assets.images.annaPortrait) },
        {
            Events.removeListener(MouseDownEvent, mouseClickListener)
            Events.removeListener(TextCloseEvent, textCloseListener)
            app.screen = PlayScreen(app, assets)
        }

    )

    init {
        if  (assets.music.music.paused) {
            assets.music.music.play()
        }
        engine.add(TextRendererUpdateSystem(textRenderer))
        Events.addListener(MouseDownEvent, mouseClickListener)
        Events.addListener(TextCloseEvent, textCloseListener)
        progressStory()
    }

    fun onMouseClick() {
        textRenderer.progress()
    }

    fun onTextClose() {
        progressStory()
    }

    fun progressStory() {
        if (storyProgress < story.size) {
            story[storyProgress++].invoke()
        }
    }

    fun message(text: String, portrait: ImageAsset? = null) {
        var parsedText = text
        val waitTimes = mutableMapOf<Int, Double>()
        val waitTimeSequence = "\\(wait (\\d+\\.\\d+)\\)"
        var matches = parsedText.match(waitTimeSequence)
        while (matches != null) {
            waitTimes[parsedText.indexOf(matches[0])] = matches[1].toDouble()
            parsedText = parsedText.replaceFirst(matches[0], "")
            matches = parsedText.match(waitTimeSequence)
        }
        engine.add(entity {
            add(Text(parsedText))
            if (portrait != null) {
                add(Portrait(portrait))
            }
            if (waitTimes.isNotEmpty()) {
                add(WaitTimes(waitTimes))
            }
        })
    }

    val renderer = AggregateRenderer(
        BaseRenderer(canvas, ctx),
        ImageRenderer(canvas, ctx, engine),
        textRenderer
    )

    override fun onRender(dt: Double) {
        renderer.onRender(dt)
    }
}