package ast

import token.Position

/*
Represents an operator expression: a + b, a * b, a / b, a - b
 */
class OperatorExpr(val left: Expression, val op: String, val right: Expression, val pos: Position) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OperatorExpr) return false

        if (left != other.left) return false
        if (op != other.op) return false
        if (right != other.right) return false
        if (pos != other.pos) return false

        return true
    }
}
