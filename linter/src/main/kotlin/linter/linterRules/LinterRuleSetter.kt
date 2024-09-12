package linter.linterRules

import com.google.gson.GsonBuilder
import linter.linterRules.rules.AllRules
import linter.linterRules.rules.CasingRule
import linter.linterRules.rules.PrintExpressionRule
import linter.linterRules.rules.ReadInputRule
import java.io.InputStream

class LinterRuleSetter {
    fun streamToConfig(stream: InputStream): LintingConfig {
        val gson =
            GsonBuilder()
                .registerTypeAdapter(LintingConfig::class.java, LinterConfigDeserializer())
                .create()
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
