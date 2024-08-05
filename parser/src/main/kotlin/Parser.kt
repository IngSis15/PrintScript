import org.example.Token
import org.example.TokenType
import org.example.ast.*

class Parser(private val tokens: List<Token>) {
    private var current: Int = 0

    fun parse(): List<ASTNode> {
        val program: MutableList<ASTNode> = mutableListOf()

        while (!isAtEnd()) {
            when (peek().type) {
                TokenType.LET_KEYWORD -> program.add(parseDeclaration())
                else -> program.add(parseExpression())
            }
        }

        return emptyList()
    }

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    private fun peek(): Token {
        return tokens[current]
    }

    private fun isAtEnd(): Boolean {
        return peek().type == TokenType.EOF
    }

    private fun previous(): Token {
        return tokens[current - 1]
    }

    private fun parseDeclaration(): DeclarationNode {
        advance() // ignore let keyword
        if (peek().type != TokenType.IDENTIFIER) throw Exception("Error parsing: Expected an identifier")

        val identifierToken = advance()

        if (peek().type != TokenType.COLON) throw Exception("Error parsing: Expected a ':'")
        advance() // ignore colon

        val type = parseTypeDeclaration()

        if (peek().type != TokenType.ASSIGNATION) throw Exception("Error parsing: Expected an '='")
        advance() // ignore assignation

        val value = parseExpression()

        return DeclarationNode(
            IdentifierNode(type, identifierToken.literal, identifierToken.start, identifierToken.end),
            value,
            identifierToken.start, // Check
            identifierToken.end // Check
        )
    }

    private fun parseTypeDeclaration(): Type {
        if (peek().type != TokenType.NUMBER_TYPE || peek().type != TokenType.STRING_TYPE) throw Exception("Error parsing: Expected type definition")
        return when (advance().type) {
            TokenType.NUMBER_TYPE -> Type.NUMBER
            TokenType.STRING_TYPE -> Type.STRING
            else -> throw Exception("Error parsing: Invalid type")
        }
    }

    // TODO: Parse Expression
    private fun parseExpression(): ASTNode {
        return LiteralNode("6", Type.NUMBER)
    }
}