package interpreter

import ast.AssignExpr
import ast.CallPrintExpr
import ast.DeclareExpr
import ast.Expression
import ast.ExpressionVisitor
import ast.IdentifierExpr
import ast.NumberExpr
import ast.OperatorExpr
import ast.StringExpr
import ast.TypeExpr

class Evaluator: ExpressionVisitor<Void, Scope> {
    fun evaluate(expression: Expression, scope: Scope) {
        expression.accept(this, scope)
    }

    override fun visit(expr: AssignExpr, context: Scope): Void {
        TODO("Not yet implemented")
    }

    override fun visit(expr: DeclareExpr, context: Scope): Void {
        TODO("Not yet implemented")
    }

    override fun visit(expr: CallPrintExpr, context: Scope): Void {
        TODO("Not yet implemented")
    }

    override fun visit(expr: IdentifierExpr, context: Scope): Void {
        TODO("Not yet implemented")
    }

    override fun visit(expr: TypeExpr, context: Scope): Void {
        TODO("Not yet implemented")
    }

    override fun visit(expr: OperatorExpr, context: Scope): Void {
        TODO("Not yet implemented")
    }

    override fun visit(expr: NumberExpr, context: Scope): Void {
        TODO("Not yet implemented")
    }

    override fun visit(expr: StringExpr, context: Scope): Void {
        TODO("Not yet implemented")
    }

}