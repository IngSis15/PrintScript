package linter.linterRules.rules

import ast.AssignExpr
import ast.CallPrintExpr
import ast.DeclareExpr
import ast.Expression
import ast.OperatorExpr
import ast.ReadInputExpr
import lib.Position
import linter.linterRules.LintingRule

class ReadInputRule(private val on: Boolean) : LintingRule {
    override fun isValid(expression: Expression): List<Pair<Boolean, Position>> {
        val results = mutableListOf<Pair<Boolean, Position>>()

        if (!on) {
            when (expression) {
                is DeclareExpr -> {
                    if (expression.value is Expression) {
                        results.addAll(isValid(expression.value!!))
                    }
                }
                is AssignExpr -> {
                    results.addAll(isValid(expression.value))
                }
                is CallPrintExpr -> {
                    results.addAll(isValid(expression.arg))
                }
                is OperatorExpr -> {
                    results.addAll(isValid(expression.left))
                    results.addAll(isValid(expression.right))
                }
                is ReadInputExpr -> {
                    if (expression.value is OperatorExpr) {
                        results.add(Pair(false, expression.pos))
                    } else {
                        results.add(Pair(true, expression.pos))
                    }
                }
            }
        }

        return results
    }
}
