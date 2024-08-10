package parser

import ast.*
import org.example.Token
import org.example.TokenType
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test


class ParserTest {
    private fun test(tokens: List<Token>, expected: List<Expression>) {
        val parser = Parser(tokens.iterator(), Grammar())
        val actual = parser.parse()
        assertEquals(expected, actual)
    }

    @Test
    fun `test simple variable`() {
        val tokens = listOf(
            Token(TokenType.IDENTIFIER, "x", 0, 0),
            Token(TokenType.SEMICOLON, ";", 0, 1),
            Token(TokenType.EOF, "", 0, 1)
        )

        val expected = listOf(IdentifierExpr("x", 0))

        test(tokens, expected)
    }

    @Test
    fun `test print call`() {
        val tokens = listOf(
            Token(TokenType.PRINT, "println", 0, 0),
            Token(TokenType.LEFT_PAR, "(", 0, 5),
            Token(TokenType.STRING_LITERAL, "hello", 0, 6),
            Token(TokenType.RIGHT_PAR, ")", 0, 12),
            Token(TokenType.SEMICOLON, ";", 0, 13),
            Token(TokenType.EOF, "", 0, 13)
        )

        val expected = listOf(CallPrintExpr(StringExpr("hello", 0), 0))

        test(tokens, expected)
    }

    @Test
    fun `test simple declaration`() {
        val tokens = listOf(
            Token(TokenType.LET_KEYWORD, "let", 0, 0),
            Token(TokenType.IDENTIFIER, "x", 0, 4),
            Token(TokenType.COLON, ":", 0, 5),
            Token(TokenType.NUMBER_TYPE, "number", 0, 6),
            Token(TokenType.ASSIGNATION, "=", 0, 9),
            Token(TokenType.NUMBER_LITERAL, "42", 0, 11),
            Token(TokenType.SEMICOLON, ";", 0, 13),
            Token(TokenType.EOF, "", 0, 13)
        )

        val expected = listOf(DeclareExpr(
            TypeExpr("x", "number", 0),
            NumberExpr(42, 0),
            0
        ))

        test(tokens, expected)
    }
}