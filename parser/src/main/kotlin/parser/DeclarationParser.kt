package parser

import ast.DeclareExpr
import ast.Expression
import ast.NumberExpr
import ast.TypeExpr
import org.example.Token
import org.example.TokenType
import parser.exception.ParseException

class DeclarationParser : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        val type = parseTypeDeclaration(parser)

        if (parser.match(TokenType.ASSIGNATION)) {
            val value = parser.parseExpression()
            return DeclareExpr(type, value, 0)
        }

        // TODO: If is not assigned return null value
        return DeclareExpr(type, NumberExpr(0, 0), 0)
    }

    private fun parseTypeDeclaration(parser: Parser): Expression {
        val name = parser.consume(TokenType.IDENTIFIER).literal
        parser.consume(TokenType.COLON)
        return if (parser.match(TokenType.STRING_TYPE)) {
            TypeExpr(name, "string", 0)
        } else if (parser.match(TokenType.NUMBER_TYPE)) {
            TypeExpr(name, "number", 0)
        } else {
            throw ParseException("Expected type declaration")
        }
    }
}
