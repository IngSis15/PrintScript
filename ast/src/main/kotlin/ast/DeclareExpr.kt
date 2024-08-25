package ast

import org.example.Position

/*
Variable declaration expression: let x: string = "hello";
 */
class DeclareExpr(val variable: Expression, val value: Expression, val pos: Position) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DeclareExpr) return false

        if (variable != other.variable) return false
        if (value != other.value) return false
        if (pos != other.pos) return false

        return true
    }
}
