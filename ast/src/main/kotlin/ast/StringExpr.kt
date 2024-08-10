package ast

/*
Represents a string expression: "hello", "world", etc.
 */
class StringExpr(val value: String, val pos: Int): Expression {
    override fun <R, C> accept(visitor: ExpressionVisitor<R, C>, context: C): R {
        return visitor.visit(this, context)
    }
}