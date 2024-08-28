package ast

import token.Position

/*
Represents a number expression: 1, 2, 3, etc.
*/
class NumberExpr(val value: Int, val pos: Position) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NumberExpr) return false

        if (value != other.value) return false
        if (pos != other.pos) return false

        return true
    }
}
