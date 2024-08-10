package parser

import ast.Expression
import ast.NumberExpr
import ast.StringExpr
import org.example.Token
import org.example.TokenType
import parser.exception.ParseException

class LiteralParser: PrefixParser {
    override fun parse(parser: Parser, token: Token): Expression {
        return when (token.type) {
            TokenType.NUMBER_LITERAL -> NumberExpr(token.literal.toInt(), token.start)
            TokenType.STRING_LITERAL -> StringExpr(token.literal, token.start)
            else -> throw ParseException("Unexpected token $token")
        }
    }
}