package runner.utils

import runner.ErrorHandler

class ErrorCollector : ErrorHandler {
    private val errors = mutableListOf<Throwable>()

    override fun handleError(error: Throwable) {
        errors.add(error)
    }

    fun getErrors(): List<Throwable> {
        return errors
    }
}
