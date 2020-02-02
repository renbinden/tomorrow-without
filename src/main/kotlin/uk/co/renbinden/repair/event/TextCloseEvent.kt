package uk.co.renbinden.repair.event

import uk.co.renbinden.ilse.event.Event
import uk.co.renbinden.ilse.event.EventMapper

class TextCloseEvent : Event {
    companion object : EventMapper<TextCloseEvent>(TextCloseEvent::class)
}