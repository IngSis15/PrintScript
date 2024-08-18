package ast

/*
Represents a string expression: "hello", "world", etc.
 */
class StringExpr(val value: String, val pos: Int) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StringExpr) return false

        if (value != other.value) return false
        if (pos != other.pos) return false

        return true
    }
}
