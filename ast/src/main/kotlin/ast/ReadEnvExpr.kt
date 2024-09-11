package ast

import lib.Position

class ReadEnvExpr(val name: Expression, val pos: Position) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ReadEnvExpr) return false

        if (name != other.name) return false

        return true
    }
}
