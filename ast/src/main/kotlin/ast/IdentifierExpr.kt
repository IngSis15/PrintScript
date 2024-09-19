package ast

import lib.Position

/*
Represents an identifier expression: a
 */
class IdentifierExpr(val name: String, override val pos: Position) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IdentifierExpr) return false

        if (name != other.name) return false
        if (pos != other.pos) return false

        return true
    }
}
