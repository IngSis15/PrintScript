package parser

import ast.DeclareExpr
import ast.Expression
import ast.NumberExpr
import ast.TypeExpr
import lib.Position
import parser.exception.ParseException
import token.Token
import token.TokenType

class DeclarationParser : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        val type = parseTypeDeclaration(parser)

        if (parser.match(TokenType.ASSIGNATION)) {
            val value = parser.parseExpression()
            return DeclareExpr(type, value, token.start)
        }

        // TODO: If is not assigned return null value
        return DeclareExpr(type, NumberExpr(0, Position(0, 0)), token.start)
    }

    private fun parseTypeDeclaration(parser: Parser): Expression {
        val identifierToken = parser.consume(TokenType.IDENTIFIER)
        parser.consume(TokenType.COLON)
        return if (parser.match(TokenType.STRING_TYPE)) {
            TypeExpr(identifierToken.literal, "string", identifierToken.start)
        } else if (parser.match(TokenType.NUMBER_TYPE)) {
            TypeExpr(identifierToken.literal, "number", identifierToken.start)
        } else {
            throw ParseException("Expected type declaration")
        }
    }
}
