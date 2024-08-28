package parser

import ast.Expression
import token.Token

interface PrefixParser {
    fun parse(
        parser: Parser,
        token: Token,
    ): Expression
}
