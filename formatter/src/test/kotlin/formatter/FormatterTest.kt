package formatter

import lexer.Lexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import source.StringReader

class FormatterTest {
    private val config =
        FormatterConfig(
            spaceBeforeColon = true,
            spaceAfterColon = true,
            spaceAroundAssignment = true,
            newLinesBeforePrintln = 1,
        )
    private val formatter = Formatter(config)
    private val lexer = Lexer("1.0")

    @Test
    fun testFormatSpaceAroundColon() {
        val input = "key:value"
        val expected = "key : value"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }

    @Test
    fun testFormatSpaceAroundAssignment() {
        val input = "a=1"
        val expected = "a = 1"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }

    @Test
    fun testFormatNewLinesBeforePrintlnWithFewerNewlines() {
        val input = "a = 1;println(\"Hello\")"
        val expected = "a = 1;\n\nprintln(\"Hello\")"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }

    @Test
    fun testFormatNewLinesBeforePrintlnWithExactNewlines() {
        val input = "a = 1;\n\nprintln(\"Hello\")"
        val expected = "a = 1;\n\nprintln(\"Hello\")"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }

    @Test
    fun testFormatNewLinesBeforePrintlnWithMoreNewlines() {
        val input = "a = 1;\n\n\nprintln(\"Hello\")"
        val expected = "a = 1;\n\nprintln(\"Hello\")"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }

    @Test
    fun testFormatNewLineAfterSemicolon() {
        val input = "a = 1; b = 2;"
        val expected = "a = 1;\nb = 2;\n"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }

    @Test
    fun testFormatSingleSpaceBetweenTokens() {
        val input = "a  =  1"
        val expected = "a = 1"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }

    @Test
    fun testFormatSpaceAroundOperators() {
        val input = "a+b"
        val expected = "a + b"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }

    @Test
    fun testFormat() {
        val input = "a=1;println(\"Hello\");b=2"
        val expected = "a = 1;\n\nprintln(\"Hello\");\nb = 2"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }

    @Test
    fun testFormatKeywords() {
        val input = "let a: Int = 1"
        val expected = "let a : Int = 1"
        val tokens = lexer.lex(StringReader(input))
        val formattedTokens = formatter.format(tokens)
        val result = TokenStringBuilder(formattedTokens).buildString()
        assertEquals(expected, result)
    }
}
