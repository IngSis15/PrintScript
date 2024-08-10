package parser

import ast.Expression
import org.example.Token

interface InfixParser {
    fun parse(parser: Parser, left: Expression, token: Token): Expression
    fun getPrecedence(): Int
}