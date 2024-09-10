package parser.parselets

import ast.DeclareExpr
import ast.Expression
import ast.NumberExpr
import lib.Position
import parser.Parser
import parser.exception.ParseException
import token.Token
import token.TokenType

class DeclarationParser : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        val name = parser.consume(TokenType.IDENTIFIER).literal
        val type = parseType(parser)

        if (parser.match(TokenType.ASSIGNATION)) {
            val value = parser.parseExpression()
            return DeclareExpr(name, type, value, true, token.start)
        }

        // TODO: If is not assigned return null value
        return DeclareExpr(name, type, NumberExpr(0, Position(0, 0)), true, token.start)
    }

    fun parseType(parser: Parser): String {
        parser.consume(TokenType.COLON)
        return if (parser.match(TokenType.STRING_TYPE)) {
            "string"
        } else if (parser.match(TokenType.NUMBER_TYPE)) {
            "number"
        } else {
            throw ParseException("Expected type declaration")
        }
    }
}
