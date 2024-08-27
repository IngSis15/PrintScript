package linter.linterRules.rules

import ast.DeclareExpr
import ast.Expression
import ast.TypeExpr
import linter.linterRules.LintingRule
import org.example.Position

class CasingRule(val camelCase: Boolean) : LintingRule { // si no es camelCase entonces es snakeCase
    override fun isValid(expression: Expression): Pair<Boolean, Position> {
        if (expression is DeclareExpr) {
            if (expression.variable is TypeExpr) {
                if (camelCase) {
                    val matchesCasing = (expression.variable as TypeExpr).name.matches(Regex("[a-z]+([A-Z][a-z]*)*"))
                    return Pair(matchesCasing, (expression.variable as TypeExpr).pos)
                } else {
                    val matchesCasing = (expression.variable as TypeExpr).name.matches(Regex("[a-z]+(_[a-z]+)*"))
                    return Pair(matchesCasing, (expression.variable as TypeExpr).pos)
                }
            }
        }
        return Pair(true, Position(0, 0))
    }
}
