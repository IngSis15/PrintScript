import lexer.Lexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import source.StringReader
import token.Position
import token.Token
import token.TokenType

class LexerTests {
    @Test
    fun testLetKeyword() {
        val lexer = Lexer()
        val tokens = lexer.lex(StringReader("let x: number;"))
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 0)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 4)),
                Token(TokenType.COLON, ":", Position(1, 5)),
                Token(TokenType.NUMBER_TYPE, "number", Position(1, 7)),
                Token(TokenType.SEMICOLON, ";", Position(1, 13)),
            )

        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 14)), tokens.next())
    }

    @Test
    fun testVariableAssignment() {
        val lexer = Lexer()
        val tokens = lexer.lex(StringReader("x = 5;"))
        val expected =
            listOf(
                Token(TokenType.IDENTIFIER, "x", Position(1, 0)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 2)),
                Token(TokenType.NUMBER_LITERAL, "5", Position(1, 4)),
                Token(TokenType.SEMICOLON, ";", Position(1, 5)),
            )

        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 6)), tokens.next())
    }

    @Test
    fun testPrintLnFunction() {
        val lexer = Lexer()
        val tokens = lexer.lex(StringReader("println(\"Hello, World!\");"))
        val expected =
            listOf(
                Token(TokenType.PRINT, "println", Position(1, 0)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 7)),
                Token(TokenType.STRING_LITERAL, "Hello, World!", Position(1, 8)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 23)),
                Token(TokenType.SEMICOLON, ";", Position(1, 24)),
            )

        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 25)), tokens.next())
    }

    @Test
    fun testArithmeticExpression() {
        val lexer = Lexer()
        val tokens = lexer.lex(StringReader("x = 5 + 3 - 2 * 4 / 2;"))
        val expected =
            listOf(
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
            )

        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 22)), tokens.next())
    }

    @Test
    fun testNewlineHandling() {
        val lexer = Lexer()
        val tokens = lexer.lex(StringReader("let x = 5;\nprintln(x);"))
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 0)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 4)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 6)),
                Token(TokenType.NUMBER_LITERAL, "5", Position(1, 8)),
                Token(TokenType.SEMICOLON, ";", Position(1, 9)),
                Token(TokenType.PRINT, "println", Position(2, 0)),
                Token(TokenType.LEFT_PAR, "(", Position(2, 7)),
                Token(TokenType.IDENTIFIER, "x", Position(2, 8)),
                Token(TokenType.RIGHT_PAR, ")", Position(2, 9)),
                Token(TokenType.SEMICOLON, ";", Position(2, 10)),
            )

        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(2, 11)), tokens.next())
    }
}
