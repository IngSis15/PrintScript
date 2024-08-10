package ast

/*
PrintScript only supports print expressions.
Represents a print expression: println(a)
 */
class CallPrintExpr(val arg: Expression, val pos: Int): Expression {
    override fun <R, C> accept(visitor: ExpressionVisitor<R, C>, context: C): R {
        return visitor.visit(this, context)
    }
}