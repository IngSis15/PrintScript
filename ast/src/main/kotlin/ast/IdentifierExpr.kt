package ast

/*
Represents an identifier expression: a
 */
class IdentifierExpr(val name: String, val pos: Int): Expression {
    override fun <R, C> accept(visitor: ExpressionVisitor<R, C>, context: C): R {
        return visitor.visit(this, context)
    }
}