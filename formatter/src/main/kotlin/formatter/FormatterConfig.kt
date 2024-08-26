package org.example.formatter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

data class FormatterConfig(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundAssignment: Boolean,
    val newLinesBeforePrintln: Int
) {
    companion object {
        fun loadFromFile(filePath: String): FormatterConfig {
            val mapper = ObjectMapper(YAMLFactory())
            return mapper.readValue(File(filePath), FormatterConfig::class.java)
        }
    }
}