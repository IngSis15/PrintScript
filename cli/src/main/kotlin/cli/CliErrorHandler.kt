package cli

import runner.ErrorHandler

class CliErrorHandler : ErrorHandler {
    override fun handleError(error: Throwable) {
        println(error.message)
    }
}
