package ast

import lib.Position

/*
PrintScript only supports print expressions.
Represents a print expression: println(a)
 */
class CallPrintExpr(val arg: Expression, val pos: Position) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CallPrintExpr) return false

        if (arg != other.arg) return false
        if (pos != other.pos) return false

        return true
    }
}
