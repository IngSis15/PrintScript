package parser

import ast.CallPrintExpr
import ast.DeclareExpr
import ast.Expression
import ast.IdentifierExpr
import ast.NumberExpr
import ast.OperatorExpr
import ast.StringExpr
import ast.TypeExpr
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.exception.ParseException
import token.Position
import token.Token
import token.TokenType

class ParserTest {
    private fun test(
        tokens: List<Token>,
        expected: List<Expression>,
    ) {
        val parser = Parser(tokens.iterator(), Grammar())
        val actual = parser.parse().asSequence().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `test simple variable`() {
        val tokens =
            listOf(
                Token(TokenType.IDENTIFIER, "x", Position(0, 0)),
                Token(TokenType.SEMICOLON, ";", Position(0, 1)),
                Token(TokenType.EOF, "", Position(0, 2)),
            )

        val expected = listOf(IdentifierExpr("x", Position(0, 0)))

        test(tokens, expected)
    }

    @Test
    fun `test print call`() {
        val tokens =
            listOf(
                Token(TokenType.PRINT, "println", Position(0, 0)),
                Token(TokenType.LEFT_PAR, "(", Position(0, 7)),
                Token(TokenType.STRING_LITERAL, "hello", Position(0, 8)),
                Token(TokenType.RIGHT_PAR, ")", Position(0, 13)),
                Token(TokenType.SEMICOLON, ";", Position(0, 14)),
                Token(TokenType.EOF, "", Position(0, 15)),
            )

        val expected = listOf(CallPrintExpr(StringExpr("hello", Position(0, 8)), Position(0, 0)))

        test(tokens, expected)
    }

    @Test
    fun `test simple declaration`() {
        val tokens =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(0, 0)),
                Token(TokenType.IDENTIFIER, "x", Position(0, 4)),
                Token(TokenType.COLON, ":", Position(0, 5)),
                Token(TokenType.NUMBER_TYPE, "number", Position(0, 6)),
                Token(TokenType.ASSIGNATION, "=", Position(0, 8)),
                Token(TokenType.NUMBER_LITERAL, "42", Position(0, 10)),
                Token(TokenType.SEMICOLON, ";", Position(0, 11)),
                Token(TokenType.EOF, "", Position(0, 11)),
            )

        val expected =
            listOf(
                DeclareExpr(
                    TypeExpr("x", "number", Position(0, 4)),
                    NumberExpr(42, Position(0, 10)),
                    Position(0, 0),
                ),
            )

        test(tokens, expected)
    }

    @Test
    fun `test unexpected token`() {
        val tokens =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(0, 0)),
                Token(TokenType.IDENTIFIER, "x", Position(0, 3)),
                Token(TokenType.COLON, ":", Position(0, 4)),
                Token(TokenType.SEMICOLON, ";", Position(0, 5)),
                Token(TokenType.EOF, "", Position(0, 5)),
            )

        val parser = Parser(tokens.iterator(), Grammar())

        assertThrows<ParseException> {
            parser.parse().next()
        }
    }

    @Test
    fun `test basic sum`() {
        val tokens =
            listOf(
                Token(TokenType.NUMBER_LITERAL, "1", Position(0, 0)),
                Token(TokenType.SUM, "+", Position(0, 2)),
                Token(TokenType.NUMBER_LITERAL, "2", Position(0, 4)),
                Token(TokenType.SEMICOLON, ";", Position(0, 5)),
                Token(TokenType.EOF, "", Position(0, 6)),
            )

        val expected =
            listOf(
                OperatorExpr(
                    NumberExpr(1, Position(0, 0)),
                    "+",
                    NumberExpr(2, Position(0, 4)),
                    Position(0, 2),
                ),
            )

        test(tokens, expected)
    }

    @Test
    fun `test basic operation with precedence`() {
        val tokens =
            listOf(
                Token(TokenType.NUMBER_LITERAL, "1", Position(0, 0)),
                Token(TokenType.SUM, "+", Position(0, 2)),
                Token(TokenType.NUMBER_LITERAL, "2", Position(0, 4)),
                Token(TokenType.MUL, "*", Position(0, 6)),
                Token(TokenType.NUMBER_LITERAL, "3", Position(0, 8)),
                Token(TokenType.SEMICOLON, ";", Position(0, 9)),
                Token(TokenType.EOF, "", Position(0, 9)),
            )

        val expected =
            listOf(
                OperatorExpr(
                    NumberExpr(1, Position(0, 0)),
                    "+",
                    OperatorExpr(
                        NumberExpr(2, Position(0, 4)),
                        "*",
                        NumberExpr(3, Position(0, 8)),
                        Position(0, 6),
                    ),
                    Position(0, 2),
                ),
            )

        test(tokens, expected)
    }

    @Test
    fun `test complex operation`() {
        val tokens =
            listOf(
                Token(TokenType.NUMBER_LITERAL, "1", Position(0, 0)),
                Token(TokenType.SUM, "+", Position(0, 2)),
                Token(TokenType.NUMBER_LITERAL, "2", Position(0, 4)),
                Token(TokenType.MUL, "*", Position(0, 6)),
                Token(TokenType.NUMBER_LITERAL, "3", Position(0, 8)),
                Token(TokenType.SUM, "+", Position(0, 10)),
                Token(TokenType.NUMBER_LITERAL, "4", Position(0, 12)),
                Token(TokenType.MUL, "*", Position(0, 14)),
                Token(TokenType.NUMBER_LITERAL, "5", Position(0, 16)),
                Token(TokenType.SEMICOLON, ";", Position(0, 17)),
                Token(TokenType.EOF, "", Position(0, 17)),
            )

        val expected =
            listOf(
                OperatorExpr(
                    OperatorExpr(
                        NumberExpr(1, Position(0, 0)),
                        "+",
                        OperatorExpr(
                            NumberExpr(2, Position(0, 4)),
                            "*",
                            NumberExpr(3, Position(0, 8)),
                            Position(0, 6),
                        ),
                        Position(0, 2),
                    ),
                    "+",
                    OperatorExpr(
                        NumberExpr(4, Position(0, 12)),
                        "*",
                        NumberExpr(5, Position(0, 16)),
                        Position(0, 14),
                    ),
                    Position(0, 10),
                ),
            )

        test(tokens, expected)
    }
}
