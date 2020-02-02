package uk.co.renbinden.repair.renderer

import org.w3c.dom.*
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.ilse.event.Events
import uk.co.renbinden.repair.component.Portrait
import uk.co.renbinden.repair.component.Text
import uk.co.renbinden.repair.component.WaitTimes
import uk.co.renbinden.repair.event.TextCloseEvent
import uk.co.renbinden.repair.palette.UIPalette.TEXT_BOX_COLOR
import uk.co.renbinden.repair.palette.UIPalette.TEXT_COLOR
import kotlin.math.max

class TextRenderer(val canvas: HTMLCanvasElement, val ctx: CanvasRenderingContext2D, val engine: Engine) : Renderer {

    companion object {
        const val TEXT_BOX_MARGIN = 16.0
        const val TEXT_BOX_PADDING = 16.0
        const val TEXT_BOX_HEIGHT = 96.0
        const val PORTRAIT_MARGIN = 16.0
        const val FONT = "20px 'Merriweather', serif"
        const val CHARACTER_WAIT_TIME = 0.02
    }

    var charactersShown = 0
    var timeToNextCharacter = CHARACTER_WAIT_TIME
    var startLineIndex = 0

    fun reset() {
        charactersShown = 0
        timeToNextCharacter = CHARACTER_WAIT_TIME
        startLineIndex = 0
    }

    fun progress() {
        if (engine.entities.any { it.has(Text) }) {
            val messageEntity = engine.entities.first { it.has(Text) }
            val text = messageEntity[Text]
            val portrait = if (messageEntity.has(Portrait)) {
                messageEntity[Portrait]
            } else {
                null
            }
            val portraitWidth = if (portrait != null) portrait.portrait.image.width + PORTRAIT_MARGIN + max(TEXT_BOX_PADDING, PORTRAIT_MARGIN) else 0.0
            var lines = getLines(
                ctx,
                text.text,
                canvas.width - (((TEXT_BOX_MARGIN + TEXT_BOX_PADDING) * 2) + portraitWidth)
            ).drop(startLineIndex).take(3)
            val linesLength = lines.sumBy(String::length)
            if (charactersShown < linesLength) {
                charactersShown = linesLength
            } else {
                charactersShown -= lines.first().length
                startLineIndex++
                lines = getLines(
                    ctx,
                    text.text,
                    canvas.width - (((TEXT_BOX_MARGIN + TEXT_BOX_PADDING) * 2) + portraitWidth)
                ).drop(startLineIndex).take(3)
                if (lines.size < 3) {
                    engine.remove(messageEntity)
                    reset()
                    Events.onEvent(TextCloseEvent())
                }
            }
        }
    }

    fun onTick(dt: Double) {
        if (engine.entities.none { it.has(Text) }) return
        val messageEntity = engine.entities.first { it.has(Text) }
        val portrait = if (messageEntity.has(Portrait)) {
            messageEntity[Portrait]
        } else {
            null
        }
        val portraitWidth = if (portrait != null) portrait.portrait.image.width + PORTRAIT_MARGIN + max(TEXT_BOX_PADDING, PORTRAIT_MARGIN) else 0.0
        if (
            charactersShown < getLines(
                ctx,
                messageEntity[Text].text,
                canvas.width - (((TEXT_BOX_MARGIN + TEXT_BOX_PADDING) * 2) + portraitWidth)
            ).drop(startLineIndex).take(3).sumBy(String::length)
        ) {
            timeToNextCharacter -= dt
            while (timeToNextCharacter <= 0.0) {
                charactersShown++
                if (messageEntity.has(WaitTimes)) {
                    val waitTimes = messageEntity[WaitTimes].waitTimes
                    val lines = getLines(
                        ctx,
                        messageEntity[Text].text,
                        canvas.width - (((TEXT_BOX_MARGIN + TEXT_BOX_PADDING) * 2) + portraitWidth)
                    )
                    val previousLines = lines.take(startLineIndex)
                    timeToNextCharacter += waitTimes[
                            charactersShown
                                    + previousLines.size
                                    + lines.drop(startLineIndex).take(3).displayCharacters(charactersShown).size - 1
                                    + previousLines.sumBy(String::length)
                    ] ?: CHARACTER_WAIT_TIME
                } else {
                    timeToNextCharacter += CHARACTER_WAIT_TIME
                }
            }
        }
    }

    override fun onRender(dt: Double) {
        if (engine.entities.any { it.has(Text) }) {
            val messageEntity = engine.entities.first { it.has(Text) }
            val text = messageEntity[Text]
            val portrait = if (messageEntity.has(Portrait)) {
                messageEntity[Portrait]
            } else {
                null
            }
            ctx.fillStyle = TEXT_BOX_COLOR
            ctx.fillRect(
                TEXT_BOX_MARGIN,
                canvas.height - (TEXT_BOX_HEIGHT + TEXT_BOX_MARGIN),
                canvas.width - (TEXT_BOX_MARGIN * 2),
                TEXT_BOX_HEIGHT
            )
            if (portrait != null) {
                ctx.drawImage(
                    portrait.portrait.image,
                    TEXT_BOX_MARGIN + max(TEXT_BOX_PADDING, PORTRAIT_MARGIN),
                    canvas.height - (TEXT_BOX_MARGIN + max(TEXT_BOX_PADDING, PORTRAIT_MARGIN) + portrait.portrait.image.height)
                )
            }
            ctx.fillStyle = TEXT_COLOR
            ctx.font = FONT
            ctx.textAlign = CanvasTextAlign.LEFT
            ctx.textBaseline = CanvasTextBaseline.TOP
            val portraitWidth = if (portrait != null) portrait.portrait.image.width + PORTRAIT_MARGIN + max(TEXT_BOX_PADDING, PORTRAIT_MARGIN) else 0.0
            val lines = getLines(
                ctx,
                text.text,
                canvas.width - (((TEXT_BOX_MARGIN + TEXT_BOX_PADDING) * 2) + portraitWidth)
            ).drop(startLineIndex).take(3).displayCharacters(charactersShown)
            for ((i, line) in lines.withIndex()) {
                ctx.fillText(
                    line,
                    TEXT_BOX_MARGIN + portraitWidth,
                    canvas.height - (TEXT_BOX_HEIGHT + TEXT_BOX_MARGIN - TEXT_BOX_PADDING) + (24 * i),
                    canvas.width - ((TEXT_BOX_MARGIN + TEXT_BOX_PADDING) * 2)
                )
            }
        }
    }

    fun getLines(ctx: CanvasRenderingContext2D, text: String, maxWidth: Double): List<String> {
        val wordsFirstPass = text.split(" ")
        val words = mutableListOf<String>()
        for (word in wordsFirstPass) {
            if (word.contains("\n")) {
                val splitWords = word.split("\n").map { splitWord -> "$splitWord\n" }.toMutableList()
                splitWords[splitWords.size - 1] = splitWords[splitWords.size - 1].replace("\n", "")
                words.addAll(splitWords)
            } else {
                words.add(word)
            }
        }
        val lines = mutableListOf<String>()
        if (words.isEmpty()) return lines
        var currentLine = ""
        for (word in words) {
            val width = ctx.measureText("$currentLine $word").width
            if (width >= maxWidth) {
                lines.add(currentLine)
                currentLine = word.replace("\n", "")
            } else {
                currentLine += if (currentLine.isEmpty()) {
                    word.replace("\n", "")
                } else {
                    " ${word.replace("\n", "")}"
                }
            }
            if (word.endsWith("\n")) {
                lines.add(currentLine)
                currentLine = ""
            }
        }
        lines.add(currentLine)
        return lines
    }

    fun List<String>.displayCharacters(characters: Int): List<String> {
        val newLines = mutableListOf<String>()
        var charactersRemaining = characters
        for (line in this) {
            if (charactersRemaining >= line.length) {
                newLines.add(line)
                charactersRemaining -= line.length
            } else {
                if (charactersRemaining > 0) {
                    newLines.add(line.substring(0, charactersRemaining))
                }
                break
            }
        }
        return newLines
    }
}