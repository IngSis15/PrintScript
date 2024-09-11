package linter.linterRules

import com.google.gson.Gson
import linter.linterRules.rules.AllRules
import linter.linterRules.rules.CasingRule
import linter.linterRules.rules.PrintExpressionRule
import linter.linterRules.rules.ReadInputRule
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

    fun setRules(
        stream: InputStream,
        version: String,
    ): LintingRule {
        val lintingRulesConfig = streamToConfig(stream)
        var listOfRules = mutableListOf<LintingRule>()
        if (lintingRulesConfig.camelCase != null) {
            val casingRule = CasingRule(lintingRulesConfig.camelCase)
            listOfRules.add(casingRule)
        }
        if (lintingRulesConfig.expressionAllowedInPrint != null) {
            val printExpressionRule = PrintExpressionRule(lintingRulesConfig.expressionAllowedInPrint)
            listOfRules.add(printExpressionRule)
        }
        if (lintingRulesConfig.expressionAllowedInReadInput != null && version == "1.1") {
            val readInputRule = ReadInputRule(lintingRulesConfig.expressionAllowedInReadInput)
            listOfRules.add(readInputRule)
        }
        return AllRules(listOfRules)
    }
}
