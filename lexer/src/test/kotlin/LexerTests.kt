
import lexer.Lexer
import org.example.Token
import org.example.TokenType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LexerTests {
    @Test
    fun testLetKeyword() {
        val lexer = Lexer()
        val tokens = lexer.lex("let x: number;")
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", 0, 3),
                Token(TokenType.IDENTIFIER, "x", 4, 5),
                Token(TokenType.COLON, ":", 5, 6),
                Token(TokenType.NUMBER_TYPE, "number", 7, 13),
                Token(TokenType.SEMICOLON, ";", 13, 14),
                Token(TokenType.EOF, "", 14, 14),
            )
        assertEquals(expected, tokens)
    }

    @Test
    fun testVariableAssignment() {
        val lexer = Lexer()
        val tokens = lexer.lex("x = 5;")
        val expected =
            listOf(
                Token(TokenType.IDENTIFIER, "x", 0, 1),
                Token(TokenType.ASSIGNATION, "=", 2, 3),
                Token(TokenType.NUMBER_LITERAL, "5", 4, 5),
                Token(TokenType.SEMICOLON, ";", 5, 6),
                Token(TokenType.EOF, "", 6, 6),
            )
        assertEquals(expected, tokens)
    }

    @Test
    fun testPrintLnFunction() {
        val lexer = Lexer()
        val tokens = lexer.lex("println(\"Hello, World!\");")
        val expected =
            listOf(
                Token(TokenType.PRINT, "println", 0, 7),
                Token(TokenType.LEFT_PAR, "(", 7, 8),
                Token(TokenType.STRING_LITERAL, "\"Hello, World!\"", 8, 23),
                Token(TokenType.RIGHT_PAR, ")", 23, 24),
                Token(TokenType.SEMICOLON, ";", 24, 25),
                Token(TokenType.EOF, "", 25, 25),
            )
        assertEquals(expected, tokens)
    }

    @Test
    fun testArithmeticExpression() {
        val lexer = Lexer()
        val tokens = lexer.lex("x = 5 + 3 - 2 * 4 / 2;")
        val expected =
            listOf(
                Token(TokenType.IDENTIFIER, "x", 0, 1),
                Token(TokenType.ASSIGNATION, "=", 2, 3),
                Token(TokenType.NUMBER_LITERAL, "5", 4, 5),
                Token(TokenType.SUM, "+", 6, 7),
                Token(TokenType.NUMBER_LITERAL, "3", 8, 9),
                Token(TokenType.SUB, "-", 10, 11),
                Token(TokenType.NUMBER_LITERAL, "2", 12, 13),
                Token(TokenType.MUL, "*", 14, 15),
                Token(TokenType.NUMBER_LITERAL, "4", 16, 17),
                Token(TokenType.DIV, "/", 18, 19),
                Token(TokenType.NUMBER_LITERAL, "2", 20, 21),
                Token(TokenType.SEMICOLON, ";", 21, 22),
                Token(TokenType.EOF, "", 22, 22),
            )
        assertEquals(expected, tokens)
    }
}
