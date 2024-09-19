package interpreter.strategies

interface OperatorStrategy {
    fun execute(
        left: Any,
        right: Any,
    ): Any
}
