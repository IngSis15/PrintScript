package parser.parselets

import ast.ConditionalExpr
import ast.Expression
import parser.Parser
import token.Token
import token.TokenType

class ConditionParser : PrefixParser {
    override fun parse(
        parser: Parser,
        token: Token,
    ): Expression {
        parser.consume(TokenType.LEFT_PAR)
        val condition = parser.parseExpression()
        parser.consume(TokenType.RIGHT_PAR)

        parser.consume(TokenType.LEFT_BRACE)

        val block = mutableListOf<Expression>()
        while (parser.current().type != TokenType.RIGHT_BRACE) {
            block += parser.parseExpression()
            parser.consume(TokenType.SEMICOLON)
        }
        parser.consume(TokenType.RIGHT_BRACE)

        val elseBlock =
            if (parser.current().type == TokenType.ELSE) {
                parser.consume(TokenType.ELSE)
                parser.consume(TokenType.LEFT_BRACE)

                val elseBlock = mutableListOf<Expression>()
                while (parser.current().type != TokenType.RIGHT_BRACE) {
                    elseBlock += parser.parseExpression()
                    parser.consume(TokenType.SEMICOLON)
                }
                parser.consume(TokenType.RIGHT_BRACE)

                elseBlock
            } else {
                emptyList()
            }

        return ConditionalExpr(condition, block, elseBlock, token.start)
    }
}
