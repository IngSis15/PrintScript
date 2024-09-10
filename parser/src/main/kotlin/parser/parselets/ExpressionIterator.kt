package parser.parselets

import ast.Expression
import parser.Parser
import token.TokenType

class ExpressionIterator(val parser: Parser) : Iterator<Expression> {
    override fun hasNext(): Boolean {
        return parser.current().type != TokenType.EOF
    }

    override fun next(): Expression {
        val expr = parser.parseExpression()
        parser.consume(TokenType.SEMICOLON)
        return expr
    }
}
