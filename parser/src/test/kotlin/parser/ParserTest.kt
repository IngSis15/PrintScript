package parser

import ast.CallPrintExpr
import ast.DeclareExpr
import ast.Expression
import ast.IdentifierExpr
import ast.NumberExpr
import ast.OperatorExpr
import ast.StringExpr
import ast.TypeExpr
import org.example.Token
import org.example.TokenType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.exception.ParseException

class ParserTest {
    private fun test(
        tokens: List<Token>,
        expected: List<Expression>,
    ) {
        val parser = Parser(tokens.iterator(), Grammar())
        val actual = parser.parse()
        assertEquals(expected, actual)
    }

    @Test
    fun `test simple variable`() {
        val tokens =
            listOf(
                Token(TokenType.IDENTIFIER, "x", 0, 0),
                Token(TokenType.SEMICOLON, ";", 0, 1),
                Token(TokenType.EOF, "", 0, 1),
            )

        val expected = listOf(IdentifierExpr("x", 0))

        test(tokens, expected)
    }

    @Test
    fun `test print call`() {
        val tokens =
            listOf(
                Token(TokenType.PRINT, "println", 0, 0),
                Token(TokenType.LEFT_PAR, "(", 0, 5),
                Token(TokenType.STRING_LITERAL, "hello", 0, 6),
                Token(TokenType.RIGHT_PAR, ")", 0, 12),
                Token(TokenType.SEMICOLON, ";", 0, 13),
                Token(TokenType.EOF, "", 0, 13),
            )

        val expected = listOf(CallPrintExpr(StringExpr("hello", 0), 0))

        test(tokens, expected)
    }

    @Test
    fun `test simple declaration`() {
        val tokens =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", 0, 0),
                Token(TokenType.IDENTIFIER, "x", 0, 4),
                Token(TokenType.COLON, ":", 0, 5),
                Token(TokenType.NUMBER_TYPE, "number", 0, 6),
                Token(TokenType.ASSIGNATION, "=", 0, 9),
                Token(TokenType.NUMBER_LITERAL, "42", 0, 11),
                Token(TokenType.SEMICOLON, ";", 0, 13),
                Token(TokenType.EOF, "", 0, 13),
            )

        val expected =
            listOf(
                DeclareExpr(
                    TypeExpr("x", "number", 0),
                    NumberExpr(42, 0),
                    0,
                ),
            )

        test(tokens, expected)
    }

    @Test
    fun `test unexpected token`() {
        val tokens =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", 0, 0),
                Token(TokenType.IDENTIFIER, "", 0, 4),
                Token(TokenType.COLON, ":", 0, 5),
                Token(TokenType.SEMICOLON, ";", 0, 6),
            )

        val parser = Parser(tokens.iterator(), Grammar())

        assertThrows<ParseException> {
            parser.parse()
        }
    }

    @Test
    fun `test basic sum`() {
        val tokens =
            listOf(
                Token(TokenType.NUMBER_LITERAL, "1", 0, 0),
                Token(TokenType.SUM, "+", 0, 2),
                Token(TokenType.NUMBER_LITERAL, "2", 0, 4),
                Token(TokenType.SEMICOLON, ";", 0, 5),
                Token(TokenType.EOF, "", 0, 5),
            )

        val expected =
            listOf(
                OperatorExpr(
                    NumberExpr(1, 0),
                    "+",
                    NumberExpr(2, 0),
                    0,
                ),
            )

        test(tokens, expected)
    }

    @Test
    fun `test basic sum with precedence`() {
        val tokens =
            listOf(
                Token(TokenType.NUMBER_LITERAL, "1", 0, 0),
                Token(TokenType.SUM, "+", 0, 2),
                Token(TokenType.NUMBER_LITERAL, "2", 0, 4),
                Token(TokenType.MUL, "*", 0, 5),
                Token(TokenType.NUMBER_LITERAL, "3", 0, 7),
                Token(TokenType.SEMICOLON, ";", 0, 8),
                Token(TokenType.EOF, "", 0, 8),
            )

        val expected =
            listOf(
                OperatorExpr(
                    NumberExpr(1, 0),
                    "+",
                    OperatorExpr(
                        NumberExpr(2, 0),
                        "*",
                        NumberExpr(3, 0),
                        0,
                    ),
                    0,
                ),
            )

        test(tokens, expected)
    }

    @Test
    fun `test complex operation`() {
        val tokens =
            listOf(
                Token(TokenType.NUMBER_LITERAL, "1", 0, 0),
                Token(TokenType.SUM, "+", 0, 2),
                Token(TokenType.NUMBER_LITERAL, "2", 0, 4),
                Token(TokenType.MUL, "*", 0, 5),
                Token(TokenType.NUMBER_LITERAL, "3", 0, 7),
                Token(TokenType.SUM, "+", 0, 8),
                Token(TokenType.NUMBER_LITERAL, "4", 0, 10),
                Token(TokenType.MUL, "*", 0, 11),
                Token(TokenType.NUMBER_LITERAL, "5", 0, 13),
                Token(TokenType.SEMICOLON, ";", 0, 14),
                Token(TokenType.EOF, "", 0, 14),
            )

        val expected =
            listOf(
                OperatorExpr(
                    OperatorExpr(
                        NumberExpr(1, 0),
                        "+",
                        OperatorExpr(
                            NumberExpr(2, 0),
                            "*",
                            NumberExpr(3, 0),
                            0,
                        ),
                        0,
                    ),
                    "+",
                    OperatorExpr(
                        NumberExpr(4, 0),
                        "*",
                        NumberExpr(5, 0),
                        0,
                    ),
                    0,
                ),
            )

        test(tokens, expected)
    }
}
