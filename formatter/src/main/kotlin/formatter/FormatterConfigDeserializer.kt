package formatter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class FormatterConfigDeserializer : JsonDeserializer<FormatterConfig> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): FormatterConfig {
        val jsonObject = json.asJsonObject

        return FormatterConfig(
            spaceBeforeColon = jsonObject.get("enforce-spacing-before-colon-in-declaration").asBoolean ?: false,
            spaceAfterColon = jsonObject.get("enforce-spacing-after-colon-in-declaration").asBoolean ?: false,
            spaceAroundAssignment = !jsonObject.get("enforce-no-spacing-around-equals").asBoolean ?: true,
            newLinesBeforePrintln = jsonObject.get("newLinesBeforePrintln").asInt ?: 0,
            indentSpaces = jsonObject.get("indent-inside-if")?.asInt ?: 4,
        )
    }
}
