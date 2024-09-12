package formatter

import com.google.gson.GsonBuilder
import java.io.File
import java.io.InputStream

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
