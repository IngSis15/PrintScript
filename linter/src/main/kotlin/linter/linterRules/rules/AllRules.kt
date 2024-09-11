package linter.linterRules.rules

import ast.Expression
import lib.Position
import linter.linterRules.LintingRule

class AllRules(val ruleSet: List<LintingRule>) : LintingRule {
    override fun isValid(expression: Expression): List<Pair<Boolean, Position>> {
        val results = mutableListOf<Pair<Boolean, Position>>()
        for (rule in ruleSet) {
            val ruleResult = rule.isValid(expression)
            for (result in ruleResult) {
                if (!result.first) {
                    results.add(result)
                }
            }
        }
        return results
    }
}
