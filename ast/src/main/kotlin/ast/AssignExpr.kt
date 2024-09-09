package ast

import lib.Position

/*
Represents an assignment expression: a = 1
 */
class AssignExpr(val left: Expression, val value: Expression, val pos: Position) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AssignExpr) return false

        if (left != other.left) return false
        if (value != other.value) return false
        if (pos != other.pos) return false

        return true
    }
}
