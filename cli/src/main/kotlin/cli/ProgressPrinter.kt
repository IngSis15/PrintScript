package cli

import runner.Event
import runner.Observer

class ProgressPrinter : Observer {
    override fun update(event: Event) {
        println("${event.type}: + ${event.message}")
    }
}
