package parser

import org.example.Token
import org.example.ast.ASTNode

interface PrefixParser {
    fun parse(parser: Parser, token: Token): ASTNode
}