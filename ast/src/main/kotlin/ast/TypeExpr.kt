package ast

import org.example.Position

/*
Represents a type declaration: a: string
 */
class TypeExpr(val name: String, val type: String, val pos: Position) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TypeExpr) return false

        if (name != other.name) return false
        if (type != other.type) return false
        if (pos != other.pos) return false

        return true
    }
}
