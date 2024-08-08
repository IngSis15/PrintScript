import org.example.Token
import org.example.TokenType
import org.example.ast.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {
    @Test
    fun `should return ast`() {
        val tokens: List<Token> = listOf(
            Token(TokenType.LET_KEYWORD, "let", 0, 2),
            Token(TokenType.IDENTIFIER, "a", 4, 4),
            Token(TokenType.COLON, ":", 4, 5),
            Token(TokenType.NUMBER_TYPE, "number", 6, 12),
            Token(TokenType.ASSIGNATION, "=", 16, 17),
            Token(TokenType.NUMBER_LITERAL, "6", 18, 19),
            Token(TokenType.SEMICOLON, ";", 20, 21),
            Token(TokenType.EOF, "eof", 22, 23)
        )

        val parser = Parser(tokens)
        val ast = parser.parse()

        val expected = listOf(
            DeclarationNode(
                IdentifierNode(Type.NUMBER, "a", 4, 4),
                LiteralNode("6", Type.NUMBER),
                0, 21
            )
        )

        assertEquals(ast, expected)
    }

    @Test
    fun `test print function`() {
        val tokens: List<Token> = listOf(
            Token(TokenType.PRINT, "print", 0, 5),
            Token(TokenType.STRING_LITERAL, "hola", 3, 6),
            Token(TokenType.EOF, "eof", 8, 8)
        )

        val parser = Parser(tokens)
        val ast = parser.parse()

        val expected = listOf(
            PrintNode(
                LiteralNode(
                    "hola",
                    Type.STRING
                )
            )
        )

        assertEquals(expected, ast)
    }
}