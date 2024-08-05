import org.example.Token
import org.example.TokenType
import org.example.ast.DeclarationNode
import org.example.ast.IdentifierNode
import org.example.ast.LiteralNode
import org.example.ast.Type
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {
    @Test
    fun `should return ast`() {
        val tokens: List<Token> = listOf(
            Token(TokenType.LET_KEYWORD, "let", 0, 3),
            Token(TokenType.COLON, ":", 4, 5),
            Token(TokenType.NUMBER_TYPE, "number", 6, 12),
            Token(TokenType.IDENTIFIER, "a", 13, 14),
            Token(TokenType.ASSIGNATION, "=", 16, 17),
            Token(TokenType.NUMBER_LITERAL, "6", 18, 19),
            Token(TokenType.SEMICOLON, ";", 20, 21),
        )

        val parser = Parser(tokens)
        val ast = parser.parse()

        val expected = listOf(
            DeclarationNode(
                IdentifierNode(Type.NUMBER, "a", 13, 14),
                LiteralNode("6", Type.NUMBER),
                0, 21

            )
        )

        assertEquals(ast, expected)
    }
}