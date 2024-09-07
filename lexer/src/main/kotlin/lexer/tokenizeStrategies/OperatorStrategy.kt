package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import source.SourceReader
import token.Position
import token.Token
import token.TokenType

class OperatorStrategy(
    private val operators: Map<Char, TokenType>,
    private val operatorChar: Char,
) : TokenizeStrategy {
    override fun lex(
        input: SourceReader,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val startColumn = column
        val type = operators[operatorChar] ?: TokenType.ILLEGAL
        input.advance()
        return Pair(Token(type, operatorChar.toString(), Position(line, startColumn)), column + 1)
    }
}
