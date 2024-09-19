package interpreter.strategies

class SubtractionStrategy : OperatorStrategy {
    override fun execute(
        left: Any,
        right: Any,
    ): Any {
        if (left is Number && right is Number) {
            return left.toDouble() - right.toDouble()
        }
        throw IllegalArgumentException("Subtraction can only be performed on numbers")
    }
}
