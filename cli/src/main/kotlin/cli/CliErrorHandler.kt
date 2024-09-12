package cli

import runner.ErrorHandler

class CliErrorHandler : ErrorHandler {
    override fun handleError(message: String) {
        println(message)
    }
}
