package ast

/*
Variable declaration expression: let x: string = "hello";
 */
class DeclareExpr(val variable: Expression, val value: Expression, val pos: Int): Expression {
    override fun <R, C> accept(visitor: ExpressionVisitor<R, C>, context: C): R {
        return visitor.visit(this, context)
    }
}