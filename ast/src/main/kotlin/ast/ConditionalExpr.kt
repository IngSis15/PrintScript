package ast

import token.Position

class ConditionalExpr(val body: List<Expression>, val elseBody: List<Expression>, val position: Position) : Expression {
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
