import lexer.Lexer
import lexer.tokenizeStrategies.NumberLiteralStrategy
import lib.Position
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import token.Token
import token.TokenType
import java.io.ByteArrayInputStream

class LexerTests {
    @Test
    fun testLetKeyword() {
        val inputStream = ByteArrayInputStream("let x: number;".toByteArray())
        val lexer = Lexer(inputStream, "1.0")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 5)),
                Token(TokenType.COLON, ":", Position(1, 6)),
                Token(TokenType.NUMBER_TYPE, "number", Position(1, 8)),
                Token(TokenType.SEMICOLON, ";", Position(1, 14)),
            )

        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 15)), tokens.next())
    }

    @Test
    fun testVariableAssignment() {
        val inputStream = ByteArrayInputStream("x = 5;".toByteArray())
        val lexer = Lexer(inputStream, "1.0")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.IDENTIFIER, "x", Position(1, 1)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 3)),
                Token(TokenType.NUMBER_LITERAL, "5", Position(1, 5)),
                Token(TokenType.SEMICOLON, ";", Position(1, 6)),
            )

        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 7)), tokens.next())
    }

    @Test
    fun testArithmeticExpression() {
        val inputStream = ByteArrayInputStream("x = 5 + 3 - 2 * 4 / 2;".toByteArray())
        val lexer = Lexer(inputStream, "1.0")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.IDENTIFIER, "x", Position(1, 1)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 3)),
                Token(TokenType.NUMBER_LITERAL, "5", Position(1, 5)),
                Token(TokenType.SUM, "+", Position(1, 7)),
                Token(TokenType.NUMBER_LITERAL, "3", Position(1, 9)),
                Token(TokenType.SUB, "-", Position(1, 11)),
                Token(TokenType.NUMBER_LITERAL, "2", Position(1, 13)),
                Token(TokenType.MUL, "*", Position(1, 15)),
                Token(TokenType.NUMBER_LITERAL, "4", Position(1, 17)),
                Token(TokenType.DIV, "/", Position(1, 19)),
                Token(TokenType.NUMBER_LITERAL, "2", Position(1, 21)),
                Token(TokenType.SEMICOLON, ";", Position(1, 22)),
            )

        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 23)), tokens.next())
    }

    @Test
    fun testNewlineHandling() {
        val inputStream = ByteArrayInputStream("let x = 5;\nprintln(x);".toByteArray())
        val lexer = Lexer(inputStream, "1.0")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 5)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 7)),
                Token(TokenType.NUMBER_LITERAL, "5", Position(1, 9)),
                Token(TokenType.SEMICOLON, ";", Position(1, 10)),
                Token(TokenType.PRINT, "println", Position(2, 1)),
                Token(TokenType.LEFT_PAR, "(", Position(2, 8)),
                Token(TokenType.IDENTIFIER, "x", Position(2, 9)),
                Token(TokenType.RIGHT_PAR, ")", Position(2, 10)),
                Token(TokenType.SEMICOLON, ";", Position(2, 11)),
            )

        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(2, 12)), tokens.next())
    }

    @Test
    fun testConstKeyword() {
        val inputStream = ByteArrayInputStream("const x = 5;".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.CONST_KEYWORD, "const", Position(1, 1)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 7)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 9)),
                Token(TokenType.NUMBER_LITERAL, "5", Position(1, 11)),
                Token(TokenType.SEMICOLON, ";", Position(1, 12)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 13)), tokens.next())
    }

    @Test
    fun testIfStatement() {
        val inputStream = ByteArrayInputStream("if (true) { println(x); }".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.IF, "if", Position(1, 1)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 4)),
                Token(TokenType.BOOLEAN_LITERAL, "true", Position(1, 5)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 9)),
                Token(TokenType.LEFT_BRACE, "{", Position(1, 11)),
                Token(TokenType.PRINT, "println", Position(1, 13)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 20)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 21)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 22)),
                Token(TokenType.SEMICOLON, ";", Position(1, 23)),
                Token(TokenType.RIGHT_BRACE, "}", Position(1, 25)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 26)), tokens.next())
    }

    @Test
    fun testBooleanDeclaration() {
        val inputStream = ByteArrayInputStream("let x: boolean = true;".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 5)),
                Token(TokenType.COLON, ":", Position(1, 6)),
                Token(TokenType.BOOLEAN_TYPE, "boolean", Position(1, 8)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 16)),
                Token(TokenType.BOOLEAN_LITERAL, "true", Position(1, 18)),
                Token(TokenType.SEMICOLON, ";", Position(1, 22)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 23)), tokens.next())
    }

    @Test
    fun testElseStatement() {
        val inputStream = ByteArrayInputStream("if (true) { println(x); } else { println(y); }".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.IF, "if", Position(1, 1)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 4)),
                Token(TokenType.BOOLEAN_LITERAL, "true", Position(1, 5)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 9)),
                Token(TokenType.LEFT_BRACE, "{", Position(1, 11)),
                Token(TokenType.PRINT, "println", Position(1, 13)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 20)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 21)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 22)),
                Token(TokenType.SEMICOLON, ";", Position(1, 23)),
                Token(TokenType.RIGHT_BRACE, "}", Position(1, 25)),
                Token(TokenType.ELSE, "else", Position(1, 27)),
                Token(TokenType.LEFT_BRACE, "{", Position(1, 32)),
                Token(TokenType.PRINT, "println", Position(1, 34)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 41)),
                Token(TokenType.IDENTIFIER, "y", Position(1, 42)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 43)),
                Token(TokenType.SEMICOLON, ";", Position(1, 44)),
                Token(TokenType.RIGHT_BRACE, "}", Position(1, 46)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 47)), tokens.next())
    }

    @Test
    fun testReadInputFunction() {
        val inputStream = ByteArrayInputStream("let x = readInput();".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 5)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 7)),
                Token(TokenType.READ_INPUT, "readInput", Position(1, 9)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 18)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 19)),
                Token(TokenType.SEMICOLON, ";", Position(1, 20)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 21)), tokens.next())
    }

    @Test
    fun testReadEnvFunction() {
        val inputStream = ByteArrayInputStream("let x = readEnv();".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val tokens = lexer.lex()
        val expected =
            listOf(
                Token(TokenType.LET_KEYWORD, "let", Position(1, 1)),
                Token(TokenType.IDENTIFIER, "x", Position(1, 5)),
                Token(TokenType.ASSIGNATION, "=", Position(1, 7)),
                Token(TokenType.READ_ENV, "readEnv", Position(1, 9)),
                Token(TokenType.LEFT_PAR, "(", Position(1, 16)),
                Token(TokenType.RIGHT_PAR, ")", Position(1, 17)),
                Token(TokenType.SEMICOLON, ";", Position(1, 18)),
            )
        assertEquals(expected, tokens.asSequence().toList())
        assertEquals(Token(TokenType.EOF, "", Position(1, 19)), tokens.next())
    }

    @Test
    fun testLexInteger() {
        val inputStream = ByteArrayInputStream("123".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val strategy = NumberLiteralStrategy()
        val token = strategy.lex(lexer)

        val expected = Token(TokenType.NUMBER_LITERAL, "123", Position(1, 1))

        assertEquals(expected, token)
    }

    @Test
    fun testLexFloat() {
        val inputStream = ByteArrayInputStream("123.456".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val strategy = NumberLiteralStrategy()
        val token = strategy.lex(lexer)

        val expected = Token(TokenType.NUMBER_LITERAL, "123.456", Position(1, 1))

        assertEquals(expected, token)
    }

    @Test
    fun testMalformedNumberWithMultipleDecimalPoints() {
        val inputStream = ByteArrayInputStream("123.45.6".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val strategy = NumberLiteralStrategy()

        val exception =
            assertThrows(IllegalArgumentException::class.java) {
                strategy.lex(lexer)
            }

        assertEquals("Malformed number: multiple decimal points", exception.message)
    }

    @Test
    fun testMalformedNumberWithTrailingDecimalPoint() {
        val inputStream = ByteArrayInputStream("123.".toByteArray())
        val lexer = Lexer(inputStream, "1.1")
        val strategy = NumberLiteralStrategy()

        val exception =
            assertThrows(IllegalArgumentException::class.java) {
                strategy.lex(lexer)
            }

        assertEquals("Malformed number: decimal point without digits", exception.message)
    }
}
