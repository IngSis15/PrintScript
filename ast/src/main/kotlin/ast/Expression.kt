package ast

interface Expression {
    fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R
}
