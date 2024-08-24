import lexer.Lexer
import org.example.Token
import org.example.TokenType
import org.example.Position
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LexerTests {
    @Test
    fun testLetKeyword() {
        val lexer = Lexer()
        val tokens = lexer.lex("let x: number;", 1)
        val expected = listOf(
            Token(TokenType.LET_KEYWORD, "let", Position(1, 0)),
            Token(TokenType.IDENTIFIER, "x", Position(1, 4)),
            Token(TokenType.COLON, ":", Position(1, 5)),
            Token(TokenType.NUMBER_TYPE, "number", Position(1, 7)),
            Token(TokenType.SEMICOLON, ";", Position(1, 13)),
            Token(TokenType.EOF, "", Position(1, 14))
        )
        assertEquals(expected, tokens)
    }

    @Test
    fun testVariableAssignment() {
        val lexer = Lexer()
        val tokens = lexer.lex("x = 5;", 1)
        val expected = listOf(
            Token(TokenType.IDENTIFIER, "x", Position(1, 0)),
            Token(TokenType.ASSIGNATION, "=", Position(1, 2)),
            Token(TokenType.NUMBER_LITERAL, "5", Position(1, 4)),
            Token(TokenType.SEMICOLON, ";", Position(1, 5)),
            Token(TokenType.EOF, "", Position(1, 6))
        )
        assertEquals(expected, tokens)
    }

    @Test
    fun testPrintLnFunction() {
        val lexer = Lexer()
        val tokens = lexer.lex("println(\"Hello, World!\");", 1)
        val expected = listOf(
            Token(TokenType.PRINT, "println", Position(1, 0)),
            Token(TokenType.LEFT_PAR, "(", Position(1, 7)),
            Token(TokenType.STRING_LITERAL, "\"Hello, World!\"", Position(1, 8)),
            Token(TokenType.RIGHT_PAR, ")", Position(1, 23)),
            Token(TokenType.SEMICOLON, ";", Position(1, 24)),
            Token(TokenType.EOF, "", Position(1, 25))
        )
        assertEquals(expected, tokens)
    }

    @Test
    fun testArithmeticExpression() {
        val lexer = Lexer()
        val tokens = lexer.lex("x = 5 + 3 - 2 * 4 / 2;", 1)
        val expected = listOf(
            Token(TokenType.IDENTIFIER, "x", Position(1, 0)),
            Token(TokenType.ASSIGNATION, "=", Position(1, 2)),
            Token(TokenType.NUMBER_LITERAL, "5", Position(1, 4)),
            Token(TokenType.SUM, "+", Position(1, 6)),
            Token(TokenType.NUMBER_LITERAL, "3", Position(1, 8)),
            Token(TokenType.SUB, "-", Position(1, 10)),
            Token(TokenType.NUMBER_LITERAL, "2", Position(1, 12)),
            Token(TokenType.MUL, "*", Position(1, 14)),
            Token(TokenType.NUMBER_LITERAL, "4", Position(1, 16)),
            Token(TokenType.DIV, "/", Position(1, 18)),
            Token(TokenType.NUMBER_LITERAL, "2", Position(1, 20)),
            Token(TokenType.SEMICOLON, ";", Position(1, 21)),
            Token(TokenType.EOF, "", Position(1, 22))
        )
        assertEquals(expected, tokens)
    }
}