package linter.linterRules

import linter.linterRules.rules.AllRules
import linter.linterRules.rules.CasingRule
import linter.linterRules.rules.PrintExpressionRule
import org.json.JSONObject

class RulesCompositeSetter {
    fun readLintingRulesConfig(): JSONObject {
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("lintingRulesConfig.json")
                ?: throw IllegalArgumentException("File not found!")
        val jsonContent = inputStream.bufferedReader().use { it.readText() }
        return JSONObject(jsonContent)
    }

    fun setRules(): LintingRule {
        val lintingRulesConfig = readLintingRulesConfig()
        val casingRule = CasingRule(lintingRulesConfig.getBoolean("camelCase"))
        val printExpressionRule = PrintExpressionRule(lintingRulesConfig.getBoolean("expressionAllowedInPrint"))
        return AllRules(listOf(casingRule, printExpressionRule))
    }
}
