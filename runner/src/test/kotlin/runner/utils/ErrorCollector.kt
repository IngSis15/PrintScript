package runner.utils

import runner.ErrorHandler

class ErrorCollector : ErrorHandler {
    private val errors = mutableListOf<String>()

    override fun handleError(message: String) {
        errors.add(message)
    }

    fun getErrors(): List<String> {
        return errors
    }
}
