package parser.parselets

import ast.Expression
import parser.Parser
import token.Token

interface InfixParser {
    fun parse(
        parser: Parser,
        left: Expression,
        token: Token,
    ): Expression

    fun getPrecedence(): Int
}
