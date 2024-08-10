package ast

/*
Represents a number expression: 1, 2, 3, etc.
*/
class NumberExpr(val value: Int, val pos: Int): Expression {
    override fun <R, C> accept(visitor: ExpressionVisitor<R, C>, context: C): R {
        return visitor.visit(this, context)
    }
}