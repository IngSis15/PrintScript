package parser

import ast.Expression
import token.Token

interface InfixParser {
    fun parse(
        parser: Parser,
        left: Expression,
        token: Token,
    ): Expression

    fun getPrecedence(): Int
}
