package ast

import lib.Position

interface Expression {
    val pos: Position

    fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R
}
