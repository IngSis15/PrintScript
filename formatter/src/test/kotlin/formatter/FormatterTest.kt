package formatter

import org.example.formatter.Formatter
import org.example.formatter.FormatterConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FormatterTest {
    private val config =
        FormatterConfig(
            spaceBeforeColon = true,
            spaceAfterColon = true,
            spaceAroundAssignment = true,
            newLinesBeforePrintln = 1,
        )
    private val formatter = Formatter(config)

    @Test
    fun testFormatSpaceBeforeColon() {
        val input = "key:value"
        val expected = "key :value"
        assertEquals(expected, formatter.formatSpaceBeforeColon(input))
    }

    @Test
    fun testFormatSpaceAfterColon() {
        val input = "key: value"
        val expected = "key: value"
        assertEquals(expected, formatter.formatSpaceAfterColon(input))
    }

    @Test
    fun testFormatSpaceAroundAssignment() {
        val input = "a=1"
        val expected = "a = 1"
        assertEquals(expected, formatter.formatSpaceAroundAssignment(input))
    }

    @Test
    fun testFormatNewLinesBeforePrintln() {
        val input = "println(\"Hello\")"
        val expected = "\nprintln(\"Hello\")"
        assertEquals(expected, formatter.formatNewLinesBeforePrintln(input))
    }

    @Test
    fun testFormatNewLineAfterSemicolon() {
        val input = "a = 1; b = 2;"
        val expected = "a = 1;\nb = 2;\n"
        assertEquals(expected, formatter.formatNewLineAfterSemicolon(input))
    }

    @Test
    fun testFormatSingleSpaceBetweenTokens() {
        val input = "a  =  1"
        val expected = "a = 1"
        assertEquals(expected, formatter.formatSingleSpaceBetweenTokens(input))
    }

    @Test
    fun testFormatSpaceAroundOperators() {
        val input = "a+b"
        val expected = "a + b"
        assertEquals(expected, formatter.formatSpaceAroundOperators(input))
    }

    @Test
    fun testFormat() {
        val input = "a=1;println(\"Hello\");b=2"
        val expected =
            """
            a = 1;
            println("Hello");
            b = 2
            """.trimIndent() // Using trimIndent() for better formatting in the test

        assertEquals(expected, formatter.format(input))
    }
}
