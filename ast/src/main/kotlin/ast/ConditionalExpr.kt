package ast

import lib.Position

class ConditionalExpr(
    val condition: Expression,
    val body: List<Expression>,
    val elseBody: List<Expression>,
    override val pos: Position,
) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConditionalExpr) return false

        return true
    }
}
