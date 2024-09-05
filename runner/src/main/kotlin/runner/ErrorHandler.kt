package runner

interface ErrorHandler {
    fun handleError(error: Throwable)
}
