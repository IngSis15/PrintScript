package parser

import ast.Expression
import ast.NumberExpr
import ast.StringExpr
import parser.exception.ParseException
import token.Token
import token.TokenType

class LiteralParser : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        return when (token.type) {
            TokenType.NUMBER_LITERAL -> NumberExpr(token.literal.toInt(), token.start)
            TokenType.STRING_LITERAL -> StringExpr(token.literal, token.start)
            else -> throw ParseException("Unexpected token $token")
        }
    }
}
