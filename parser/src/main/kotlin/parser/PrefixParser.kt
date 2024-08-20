package parser

import ast.Expression
import org.example.Token

interface PrefixParser {
    fun parse(
        parser: Parser,
        token: Token,
    ): Expression
}
