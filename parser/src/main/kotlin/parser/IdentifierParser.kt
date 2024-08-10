package parser

import org.example.Token
import org.example.ast.ASTNode
import org.example.ast.IdentifierNode
import org.example.ast.Type

class IdentifierParser: PrefixParser {
    override fun parse(parser: Parser, token: Token): ASTNode {
        return IdentifierNode(Type.STRING, token.literal, token.start, token.end)
    }
}