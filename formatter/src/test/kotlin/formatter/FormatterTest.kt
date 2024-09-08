package formatter

import lexer.Lexer
import lib.TokenWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringWriter

class FormatterTest {
    private val config =
        FormatterConfig(
            spaceBeforeColon = true,
            spaceAfterColon = true,
            spaceAroundAssignment = true,
            newLinesBeforePrintln = 1,
        )
    private val formatter = Formatter(config)

    fun testFormatter(
        input: String,
        expected: String,
    ) {
        val lexer = Lexer(input.byteInputStream(), "1.0")
        val tokens = lexer.lex()
        val formattedTokens = formatter.format(tokens)

        val result = StringWriter()
        TokenWriter(formattedTokens, result).write()

        assertEquals(expected, result.toString())
    }

    @Test
    fun testFormatSpaceAroundColon() {
        val input = "key:value"
        val expected = "key : value"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatSpaceAroundAssignment() {
        val input = "a=1"
        val expected = "a = 1"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatNewLinesBeforePrintlnWithFewerNewlines() {
        val input = "a = 1;println(\"Hello\")"
        val expected = "a = 1;\n\nprintln(\"Hello\")"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatNewLinesBeforePrintlnWithExactNewlines() {
        val input = "a = 1;\n\nprintln(\"Hello\")"
        val expected = "a = 1;\n\nprintln(\"Hello\")"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatNewLinesBeforePrintlnWithMoreNewlines() {
        val input = "a = 1;\n\n\nprintln(\"Hello\")"
        val expected = "a = 1;\n\nprintln(\"Hello\")"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatNewLineAfterSemicolon() {
        val input = "a = 1; b = 2;"
        val expected = "a = 1;\nb = 2;\n"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatSingleSpaceBetweenTokens() {
        val input = "a  =  1"
        val expected = "a = 1"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatSpaceAroundOperators() {
        val input = "a+b"
        val expected = "a + b"

        testFormatter(input, expected)
    }

    @Test
    fun testFormat() {
        val input = "a=1;println(\"Hello\");b=2"
        val expected = "a = 1;\n\nprintln(\"Hello\");\nb = 2"

        testFormatter(input, expected)
    }

    @Test
    fun testFormatKeywords() {
        val input = "let a: Int = 1"
        val expected = "let a : Int = 1"

        testFormatter(input, expected)
    }
}
