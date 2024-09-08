package linter.linterRules.rules

import ast.Expression
import lib.Position
import linter.linterRules.LintingRule

class PrintExpressionRule(private val on: Boolean) : LintingRule {
    override fun isValid(expression: Expression): Pair<Boolean, Position> {
        if (!on) {
            if (expression is ast.CallPrintExpr) {
                if (expression.arg is ast.OperatorExpr) {
                    return Pair(false, expression.pos)
                }
                return Pair(true, expression.pos)
            }
        }
        return Pair(true, Position(0, 0))
    }
}
