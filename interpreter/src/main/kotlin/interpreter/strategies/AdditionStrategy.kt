package interpreter.strategies

class AdditionStrategy : OperatorStrategy {
    override fun execute(
        left: Any,
        right: Any,
    ): Any {
        return when {
            left is Number && right is Number -> left.toDouble() + right.toDouble()
            left is String && right is String -> left + right
            left is String && right is Number -> left + numberToString(right)
            left is Number && right is String -> numberToString(left) + right
            else -> throw IllegalArgumentException("Invalid operands for addition")
        }
    }

    private fun numberToString(number: Number): String {
        return if (number.toDouble() % 1 == 0.0) {
            number.toInt().toString()
        } else {
            number.toString()
        }
    }
}
