package runner

interface ErrorHandler {
    fun handleError(error: Event)
}
