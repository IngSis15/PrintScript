package runner.utils

import lib.InputProvider
import java.util.Queue

class QueueInputProvider(private val queue: Queue<String>) : InputProvider {
    override fun input(): String {
        return queue.poll()
    }
}
