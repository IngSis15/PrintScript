package cli

import runner.ErrorHandler
import runner.Event

class CliErrorHandler : ErrorHandler {
    override fun handleError(error: Event) {
        println(error.message)
    }
}
