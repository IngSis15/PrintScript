package linter

import ast.Expression
import linter.linterRules.LinterRuleSetter
import java.io.InputStream

class Linter(config: InputStream) {
    private val ruleSet = LinterRuleSetter().setRules(config)

    fun lint(expressionList: Iterator<Expression>): LinterResult {
        val listOfMessages = mutableListOf<String>()
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
