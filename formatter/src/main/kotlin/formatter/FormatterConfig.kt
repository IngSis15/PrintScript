package formatter

import com.google.gson.Gson
import java.io.File
import java.io.InputStream

data class FormatterConfig(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundAssignment: Boolean,
    val newLinesBeforePrintln: Int,
) {
    companion object {
        fun fileToConfig(file: File): FormatterConfig {
            // Initialize Gson instance
            val gson = Gson()

            // Read JSON content from the file
            val jsonContent = file.readText()

            // Parse JSON into formatter.FormatterConfig object
            return gson.fromJson(jsonContent, FormatterConfig::class.java)
        }

        fun streamToConfig(stream: InputStream): FormatterConfig {
            // Initialize Gson instance
            val gson = Gson()

            // Read JSON content from the stream
            val jsonContent = stream.bufferedReader().use { it.readText() }

            // Parse JSON into formatter.FormatterConfig object
            return gson.fromJson(jsonContent, FormatterConfig::class.java)
        }
    }
}
