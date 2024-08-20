package parser

import ast.Expression
import ast.OperatorExpr
import org.example.Token
import org.example.TokenType

class BinaryOperatorParser(private val precedence: Int) : InfixParser {
    override fun parse(
        parser: Parser,
        left: Expression,
        token: Token,
    ): Expression {
        val right = parser.parsePrecedence(precedence)
        parser.matchAny(
            TokenType.SUM,
            TokenType.SUB,
            TokenType.MUL,
            TokenType.DIV,
        )
        return OperatorExpr(left, token.literal, right, token.start)
    }

    override fun getPrecedence(): Int {
        return precedence
    }
}
