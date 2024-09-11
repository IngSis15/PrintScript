package interpreter.utils

import lib.InputProvider

class QueueInputProvider : InputProvider {
    private val queue = mutableListOf<String>()

    fun addInput(input: String) {
        queue.add(input)
    }

    override fun input(message: String): String {
        return queue.removeAt(0)
    }
}
