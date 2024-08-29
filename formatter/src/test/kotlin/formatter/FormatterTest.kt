package formatter

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
    fun testFormatNewLinesBeforePrintlnWithFewerNewlines() {
        val input = "a = 1;println(\"Hello\")"
        val expected =
            """
            a = 1;

            println("Hello")
            """.trimIndent()
        assertEquals(expected, formatter.format(input))
    }

    @Test
    fun testFormatNewLinesBeforePrintlnWithExactNewlines() {
        val input = "a = 1;\n\nprintln(\"Hello\")"
        val expected =
            """
            a = 1;

            println("Hello")
            """.trimIndent()
        assertEquals(expected, formatter.format(input))
    }

    @Test
    fun testFormatNewLinesBeforePrintlnWithMoreNewlines() {
        val input = "a = 1;\n\n\nprintln(\"Hello\")"
        val expected =
            """
            a = 1;

            println("Hello")
            """.trimIndent()
        assertEquals(expected, formatter.format(input))
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
            """.trimIndent()

        assertEquals(expected, formatter.format(input))
    }
}
