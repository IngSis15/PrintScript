package parser.parselets

import ast.BooleanExpr
import ast.Expression
import ast.NumberExpr
import ast.StringExpr
import parser.Parser
import parser.exception.ParseException
import token.Token
import token.TokenType

class LiteralParser : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        return when (token.type) {
            TokenType.NUMBER_LITERAL -> NumberExpr(token.literal.toDouble(), token.start)
            TokenType.STRING_LITERAL -> StringExpr(token.literal, token.start)
            TokenType.BOOLEAN_LITERAL -> BooleanExpr(getBoolean(token), token.start)
            else -> throw ParseException("Unexpected token $token")
        }
    }

    private fun getBoolean(token: Token): Boolean {
        return when (token.literal) {
            "true" -> true
            "false" -> false
            else -> throw ParseException("Unexpected boolean: $token")
        }
    }
}
