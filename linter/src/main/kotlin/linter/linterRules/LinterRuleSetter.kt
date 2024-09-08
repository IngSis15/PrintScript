package linter.linterRules

import com.google.gson.Gson
import linter.linterRules.rules.AllRules
import linter.linterRules.rules.CasingRule
import linter.linterRules.rules.PrintExpressionRule
import java.io.File
import java.io.InputStream

class LinterRuleSetter {
    private fun fileToConfig(path: String): LintingConfig {
        val file = File(path)
        val gson = Gson()
        val jsonContent = file.readText()
        return gson.fromJson(jsonContent, LintingConfig::class.java)
    }

    private fun streamToConfig(stream: InputStream): LintingConfig {
        val gson = Gson()
        val jsonContent = stream.bufferedReader().use { it.readText() }
        return gson.fromJson(jsonContent, LintingConfig::class.java)
    }

    fun setRules(stream: InputStream): LintingRule {
        val lintingRulesConfig = streamToConfig(stream)
        val casingRule = CasingRule(lintingRulesConfig.camelCase)
        val printExpressionRule = PrintExpressionRule(lintingRulesConfig.expressionAllowedInPrint)
        return AllRules(listOf(casingRule, printExpressionRule))
    }
}
