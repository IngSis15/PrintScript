package parser.parselets

import ast.DeclareExpr
import ast.Expression
import parser.Parser
import parser.exception.ParseException
import token.Token
import token.TokenType

class DeclarationParser(val mutable: Boolean) : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        val name = parser.consume(TokenType.IDENTIFIER).literal
        val type = parseType(parser)

        if (parser.match(TokenType.ASSIGNATION)) {
            val value = parser.parseExpression()
            return DeclareExpr(name, type, value, mutable, token.start)
        }

        return DeclareExpr(name, type, null, mutable, token.start)
    }

    fun parseType(parser: Parser): String {
        parser.consume(TokenType.COLON)
        return if (parser.match(TokenType.STRING_TYPE)) {
            "string"
        } else if (parser.match(TokenType.NUMBER_TYPE)) {
            "number"
        } else if (parser.match(TokenType.BOOLEAN_TYPE)) {
            "boolean"
        } else {
            throw ParseException("Expected type declaration")
        }
    }
}
