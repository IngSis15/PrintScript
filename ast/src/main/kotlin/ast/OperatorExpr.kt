package ast

/*
Represents an operator expression: a + b, a * b, a / b, a - b
 */
class OperatorExpr(val left: Expression, val op: String, val right: Expression, val pos: Int): Expression {
    override fun <R, C> accept(visitor: ExpressionVisitor<R, C>, context: C): R {
        return visitor.visit(this, context)
    }
}