package parser

import ast.Expression
import ast.IdentifierExpr
import org.example.Token

class IdentifierParser : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        return IdentifierExpr(token.literal, token.start)
    }
}
