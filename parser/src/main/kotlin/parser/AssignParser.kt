package parser

import ast.AssignExpr
import ast.Expression
import org.example.Token

class AssignParser(private val precedence: Int): InfixParser {
    override fun parse(parser: Parser, left: Expression, token: Token): Expression {
        val value = parser.parseExpression()
        return AssignExpr(left, value, token.start)
    }

    override fun getPrecedence(): Int {
        return precedence
    }
}