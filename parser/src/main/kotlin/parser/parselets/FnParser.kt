package parser.parselets

import ast.CallPrintExpr
import ast.Expression
import ast.ReadEnvExpr
import ast.ReadInputExpr
import parser.Parser
import token.Token
import token.TokenType

class FnParser : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        parser.consume(TokenType.LEFT_PAR)
        val args = parser.parseExpression()
        parser.consume(TokenType.RIGHT_PAR)

        return when (token.type) {
            TokenType.PRINT -> CallPrintExpr(args, token.start)
            TokenType.READ_INPUT -> ReadInputExpr(args, token.start)
            TokenType.READ_ENV -> ReadEnvExpr(args, token.start)
            else -> throw IllegalArgumentException("Unknown function type: ${token.type}")
        }
    }
}
