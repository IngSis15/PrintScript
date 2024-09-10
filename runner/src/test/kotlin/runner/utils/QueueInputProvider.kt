package runner.utils

import lib.InputProvider

class QueueInputProvider : InputProvider {
    private val queue = mutableListOf<String>()

    fun addInput(input: String) {
        queue.add(input)
    }

    override fun input(): String {
        return queue.removeAt(0)
    }
}
