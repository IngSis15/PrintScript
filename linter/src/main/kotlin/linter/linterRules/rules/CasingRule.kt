package linter.linterRules.rules

import ast.DeclareExpr
import ast.Expression
import lib.Position
import linter.linterRules.LintingRule

class CasingRule(private val camelCase: Boolean) : LintingRule { // si no es camelCase entonces es snakeCase
    override fun isValid(expression: Expression): Pair<Boolean, Position> {
        if (expression is DeclareExpr) {
            val name = expression.name
            if (camelCase) {
                val matchesCasing = name.matches(Regex("[a-z]+([A-Z][a-z]*)*"))
                return Pair(matchesCasing, expression.pos)
            } else {
                val matchesCasing = name.matches(Regex("[a-z]+(_[a-z]+)*"))
                return Pair(matchesCasing, expression.pos)
            }
        }
        return Pair(true, Position(0, 0))
    }
}
