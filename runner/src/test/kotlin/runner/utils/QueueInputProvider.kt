package runner.utils

import lib.InputProvider
import java.util.Queue

class QueueInputProvider(private val queue: Queue<String>) : InputProvider {
    override fun input(message: String): String {
        return queue.poll()
    }
}
