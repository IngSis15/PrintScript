package linter

import ast.Expression
import linter.linterRules.RulesCompositeSetter

class Linter {
    fun lint(expressionList: List<Expression>): LinterResult {
        val listOfMessages = mutableListOf<String>()
        val ruleSet = RulesCompositeSetter().setRules()
        expressionList.forEach { expression ->
            val (valid, pos) = ruleSet.isValid(expression)
            if (!valid) {
                listOfMessages.addLast("Error at line ${pos.line} and position ${pos.column}")
            }
        }
        if (listOfMessages.size == 0) {
            return LinterResult(true, listOf("Linting passed successfully!"))
        }
        return LinterResult(false, listOfMessages)
    }
}
