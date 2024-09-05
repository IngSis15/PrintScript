package runner.utils

import runner.ErrorHandler
import runner.Event

class ErrorCollector : ErrorHandler {
    private val errors = mutableListOf<Event>()

    override fun handleError(error: Event) {
        errors.add(error)
    }

    fun getErrors(): List<Event> {
        return errors
    }
}
