package interpreter.strategies

class DivisionStrategy : OperatorStrategy {
    override fun execute(
        left: Any,
        right: Any,
    ): Any {
        if (left is Number && right is Number) {
            if (right.toDouble() == 0.0) {
                throw ArithmeticException("Division by zero")
            }
            return left.toDouble() / right.toDouble()
        }
        throw IllegalArgumentException("Division can only be performed on numbers")
    }
}
