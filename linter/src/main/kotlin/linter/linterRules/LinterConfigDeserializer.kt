package linter.linterRules

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class LinterConfigDeserializer : JsonDeserializer<LintingConfig> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): LintingConfig {
        val jsonObject = json.asJsonObject
        val identifierFormat =
            if (jsonObject.get("identifier_format") != null) jsonObject.get("identifier_format").asString == "camel case" else null
        val mandatoryVariableOrLiteralInPrint =
            if (jsonObject.get("mandatory-variable-or-literal-in-println") != null) {
                !jsonObject.get(
                    "mandatory-variable-or-literal-in-println",
                ).asBoolean
            } else {
                null
            }
        val mandatoryVariableOrLiteralInReadInput =
            if (jsonObject.get("mandatory-variable-or-literal-in-readInput") != null) {
                !jsonObject.get(
                    "mandatory-variable-or-literal-in-readInput",
                ).asBoolean
            } else {
                null
            }
        return LintingConfig(
            camelCase = identifierFormat,
            expressionAllowedInPrint = mandatoryVariableOrLiteralInPrint,
            expressionAllowedInReadInput = mandatoryVariableOrLiteralInReadInput,
        )
    }
}
