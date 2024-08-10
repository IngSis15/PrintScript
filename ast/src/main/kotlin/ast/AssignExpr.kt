package ast

/*
Represents an assignment expression: a = 1
 */
class AssignExpr(val name: String, val value: Expression, val pos: Int): Expression {
    override fun <R, C> accept(visitor: ExpressionVisitor<R, C>, context: C): R {
       return visitor.visit(this, context)
    }
}