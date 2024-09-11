package parser.parselets

import ast.Expression
import parser.Parser
import token.Token

interface PrefixParser {
    fun parse(
        parser: Parser,
        token: Token,
    ): Expression
}
