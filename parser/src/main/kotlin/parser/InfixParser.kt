package parser

import org.example.Token
import org.example.ast.ASTNode

interface InfixParser {
    fun parse(parser: Parser, left: ASTNode, token: Token)
}