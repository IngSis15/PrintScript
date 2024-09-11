package linter

import ast.Expression
import linter.linterRules.LinterRuleSetter
import java.io.InputStream

class Linter(config: InputStream, version: String) {
    private val ruleSet = LinterRuleSetter().setRules(config, version)

    fun lint(expressionList: Iterator<Expression>): LinterResult {
        val listOfMessages = mutableListOf<String>()
        expressionList.forEach { expression ->
            val ruleResults = ruleSet.isValid(expression)
            if (ruleResults.size != 0) {
                for (result in ruleResults) {
                    val pos = result.second
                    listOfMessages.add("Error at line ${pos.line} and position ${pos.column}")
                }
            }
        }
        if (listOfMessages.size == 0) {
            return LinterResult(true, listOf("Linting passed successfully!"))
        }
        return LinterResult(false, listOfMessages)
    }
}
