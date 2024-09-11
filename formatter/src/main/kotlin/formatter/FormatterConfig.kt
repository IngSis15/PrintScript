package formatter

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.io.File
import java.io.InputStream
import java.lang.reflect.Type

data class FormatterConfig(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundAssignment: Boolean,
    val newLinesBeforePrintln: Int,
    val indentSpaces: Int = 4,
) {
    companion object {
        fun fileToConfig(file: File): FormatterConfig {
            val gson =
                GsonBuilder()
                    .registerTypeAdapter(FormatterConfig::class.java, FormatterConfigDeserializer())
                    .create()
            val jsonContent = file.readText()
            return gson.fromJson(jsonContent, FormatterConfig::class.java)
        }

        fun streamToConfig(stream: InputStream): FormatterConfig {
            val gson =
                GsonBuilder()
                    .registerTypeAdapter(FormatterConfig::class.java, FormatterConfigDeserializer())
                    .create()
            val jsonContent = stream.bufferedReader().use { it.readText() }
            return gson.fromJson(jsonContent, FormatterConfig::class.java)
        }
    }
}

class FormatterConfigDeserializer : JsonDeserializer<FormatterConfig> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): FormatterConfig {
        val jsonObject = json.asJsonObject

        return FormatterConfig(
            spaceBeforeColon = jsonObject.get("spaceBeforeColon").asBoolean,
            spaceAfterColon = jsonObject.get("spaceAfterColon").asBoolean,
            spaceAroundAssignment = jsonObject.get("spaceAroundAssignment").asBoolean,
            newLinesBeforePrintln = jsonObject.get("newLinesBeforePrintln").asInt,
            indentSpaces = jsonObject.get("indentSpaces")?.asInt ?: 4,
        )
    }
}
