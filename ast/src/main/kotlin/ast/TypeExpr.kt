package ast

/*
Represents a type declaration: a: string
 */
class TypeExpr(val name: String, val type: String, val pos: Int): Expression {
    override fun <R, C> accept(visitor: ExpressionVisitor<R, C>, context: C): R {
        return visitor.visit(this, context)
    }
}