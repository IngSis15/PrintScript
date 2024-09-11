package runner.utils

import java.util.LinkedList
import java.util.Queue

class Queues {
    companion object {
        fun <T> toQueue(elements: List<T>): Queue<T> {
            return LinkedList(elements)
        }
    }
}
