package parser

import ast.Expression
import ast.OperatorExpr
import org.example.Token

class BinaryOperatorParser(private val precedence: Int) : InfixParser {
    override fun parse(
        parser: Parser,
        left: Expression,
        token: Token,
    ): Expression {
        val right = parser.parsePrecedence(precedence)
        return OperatorExpr(left, token.literal, right, token.start)
    }

    override fun getPrecedence(): Int {
        return precedence
    }
}
