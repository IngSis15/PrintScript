package parser

import ast.CallPrintExpr
import ast.Expression
import token.Token
import token.TokenType

class PrintCallParser : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        parser.consume(TokenType.LEFT_PAR)
        val args = parser.parseExpression()
        parser.consume(TokenType.RIGHT_PAR)
        return CallPrintExpr(args, token.start)
    }
}
