package ast

interface ExpressionVisitor<R, C> {
    fun visit(
        expr: AssignExpr,
        context: C,
    ): R

    fun visit(
        expr: DeclareExpr,
        context: C,
    ): R

    fun visit(
        expr: CallPrintExpr,
        context: C,
    ): R

    fun visit(
        expr: IdentifierExpr,
        context: C,
    ): R

    fun visit(
        expr: TypeExpr,
        context: C,
    ): R

    fun visit(
        expr: OperatorExpr,
        context: C,
    ): R

    fun visit(
        expr: NumberExpr,
        context: C,
    ): R

    fun visit(
        expr: StringExpr,
        context: C,
    ): R
}
