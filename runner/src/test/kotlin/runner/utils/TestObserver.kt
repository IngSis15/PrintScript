package runner.utils

import runner.Event
import runner.Observer

class TestObserver : Observer {
    private val events = mutableListOf<Event>()

    override fun update(event: Event) {
        events.add(event)
    }

    fun getEvents(): List<Event> {
        return events
    }
}
