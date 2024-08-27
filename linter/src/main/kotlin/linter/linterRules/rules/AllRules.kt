package linter.linterRules.rules

import ast.Expression
import linter.linterRules.LintingRule
import org.example.Position

class AllRules(val ruleSet: List<LintingRule>) : LintingRule {
    override fun isValid(expression: Expression): Pair<Boolean, Position> {
        for (rule in ruleSet) {
            val (valid, pos) = rule.isValid(expression)
            if (!valid) {
                return Pair(false, pos)
            }
        }
        return Pair(true, Position(0, 0))
    }
}
