package parser

import ast.ConditionalExpr
import ast.Expression
import parser.exception.SemanticException
import parser.semantic.SemanticAnalyzer
import token.TokenType

class ExpressionIterator(val parser: Parser) : Iterator<Expression> {
    private val semanticAnalyzer = SemanticAnalyzer()

    override fun hasNext(): Boolean {
        return parser.current().type != TokenType.EOF
    }

    override fun next(): Expression {
        val expr = parser.parseExpression()

        if (expr !is ConditionalExpr) {
            parser.consume(TokenType.SEMICOLON)
        }

        val semanticResult = semanticAnalyzer.analyze(expr)
        if (!semanticResult.success) {
            throw SemanticException("Semantic error: ${semanticResult.message} on position ${semanticResult.position}")
        }

        return expr
    }
}
