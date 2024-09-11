package formatter

import lexer.Lexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.TokenWriter
import java.io.StringWriter

class FormatterTest11 {
    private val config =
        FormatterConfig(
            spaceBeforeColon = true,
            spaceAfterColon = true,
            spaceAroundAssignment = true,
            newLinesBeforePrintln = 1,
            indentSpaces = 4,
        )

    fun testFormatter(
        input: String,
        expected: String,
    ) {
        val lexer = Lexer(input.byteInputStream(), "1.1")
        val tokens = lexer.lex()
        val formattedTokens = Formatter(config).format(tokens)

        val result = StringWriter()
        TokenWriter(formattedTokens, result).write()

        assertEquals(expected, result.toString())
    }

    @Test
    fun testFormatIfBlockIndentation() {
        val input = "if (true) {\nprintln(\"Positive\");\n}"
        val expected = "if (true){\n\n    println(\"Positive\");\n}"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatIfBlockSameLineBrace() {
        val input = "if (true)\n{\nprintln(\"Positive\");\n}"
        val expected = "if (true){\n\n    println(\"Positive\");\n}"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatNestedIfBlockIndentation() {
        val input = "if (true) {\nif (true) {\nprintln(\"Positive\");\n}\n}"
        val expected = "if (true){\n    if (true){\n\n        println(\"Positive\");\n    }\n}"

        testFormatter(input, expected)
    }
}
