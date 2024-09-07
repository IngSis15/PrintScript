import lexer.Lexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.Position
import token.Token
import token.TokenType
import java.io.ByteArrayInputStream

class LexerTests {
    @Test
    fun testLetKeyword() {
        val lexer = Lexer("1.0")
        val inputStream = ByteArrayInputStream("let x: number;".toByteArray())
        val tokens = lexer.lex(inputStream)
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
        val lexer = Lexer("1.0")
        val inputStream = ByteArrayInputStream("x = 5;".toByteArray())
        val tokens = lexer.lex(inputStream)
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
        val lexer = Lexer("1.0")
        val inputStream = ByteArrayInputStream("println(\"Hello, World!\");".toByteArray())
        val tokens = lexer.lex(inputStream)
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
        val lexer = Lexer("1.0")
        val inputStream = ByteArrayInputStream("x = 5 + 3 - 2 * 4 / 2;".toByteArray())
        val tokens = lexer.lex(inputStream)
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
        val lexer = Lexer("1.0")
        val inputStream = ByteArrayInputStream("let x = 5;\nprintln(x);".toByteArray())
        val tokens = lexer.lex(inputStream)
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

    @Test
    fun testConstKeyword() {
        val lexer = Lexer("1.1")
        val inputStream = ByteArrayInputStream("const x = 5;".toByteArray())
        val tokens = lexer.lex(inputStream)
        val expected =
            listOf(
                Token(TokenType.CONST_KEYWORD, "const", Position(1, 0)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 6)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 8)),
                Token(TokenType.NUMBER_LITERAL, "5", Position(1, 10)),
                Token(TokenType.SEMICOLON, ";", Position(1, 11)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 12)), tokens.next())
    }

    @Test
    fun testIfStatement() {
        val lexer = Lexer("1.1")
        val inputStream = ByteArrayInputStream("if (true) { println(x); }".toByteArray())
        val tokens = lexer.lex(inputStream)
        val expected =
            listOf(
                Token(TokenType.IF, "if", Position(1, 0)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 3)),
                Token(TokenType.BOOLEAN_LITERAL, "true", Position(1, 4)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 8)),
                Token(TokenType.LEFT_BRACE, "{", Position(1, 10)),
                Token(TokenType.PRINT, "println", Position(1, 12)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 19)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 20)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 21)),
                Token(TokenType.SEMICOLON, ";", Position(1, 22)),
                Token(TokenType.RIGHT_BRACE, "}", Position(1, 24)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 25)), tokens.next())
    }

    @Test
    fun testBooleanDeclaration() {
        val lexer = Lexer("1.1")
        val inputStream = ByteArrayInputStream("let x: boolean = true;".toByteArray())
        val tokens = lexer.lex(inputStream)
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 0)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 4)),
                Token(TokenType.COLON, ":", Position(1, 5)),
                Token(TokenType.BOOLEAN_TYPE, "boolean", Position(1, 7)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 15)),
                Token(TokenType.BOOLEAN_LITERAL, "true", Position(1, 17)),
                Token(TokenType.SEMICOLON, ";", Position(1, 21)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 22)), tokens.next())
    }

    @Test
    fun testElseStatement() {
        val lexer = Lexer("1.1")
        val inputStream = ByteArrayInputStream("if (true) { println(x); } else { println(y); }".toByteArray())
        val tokens = lexer.lex(inputStream)
        val expected =
            listOf(
                Token(TokenType.IF, "if", Position(1, 0)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 3)),
                Token(TokenType.BOOLEAN_LITERAL, "true", Position(1, 4)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 8)),
                Token(TokenType.LEFT_BRACE, "{", Position(1, 10)),
                Token(TokenType.PRINT, "println", Position(1, 12)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 19)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 20)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 21)),
                Token(TokenType.SEMICOLON, ";", Position(1, 22)),
                Token(TokenType.RIGHT_BRACE, "}", Position(1, 24)),
                Token(TokenType.ELSE, "else", Position(1, 26)),
                Token(TokenType.LEFT_BRACE, "{", Position(1, 31)),
                Token(TokenType.PRINT, "println", Position(1, 33)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 40)),
                Token(TokenType.IDENTIFIER, "y", Position(1, 41)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 42)),
                Token(TokenType.SEMICOLON, ";", Position(1, 43)),
                Token(TokenType.RIGHT_BRACE, "}", Position(1, 45)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 46)), tokens.next())
    }

    @Test
    fun testReadInputFunction() {
        val lexer = Lexer("1.1")
        val inputStream = ByteArrayInputStream("let x = readInput();".toByteArray())
        val tokens = lexer.lex(inputStream)
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 0)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 4)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 6)),
                Token(TokenType.READ_INPUT, "readInput", Position(1, 8)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 17)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 18)),
                Token(TokenType.SEMICOLON, ";", Position(1, 19)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 20)), tokens.next())
    }

    @Test
    fun testReadEnvFunction() {
        val lexer = Lexer("1.1")
        val inputStream = ByteArrayInputStream("let x = readEnv();".toByteArray())
        val tokens = lexer.lex(inputStream)
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 0)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 4)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 6)),
                Token(TokenType.READ_ENV, "readEnv", Position(1, 8)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 15)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 16)),
                Token(TokenType.SEMICOLON, ";", Position(1, 17)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 18)), tokens.next())
    }
}