package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import token.Position
import token.Token
import token.TokenType
import java.io.InputStream

class OperatorStrategy(
    private val operators: Map<Char, TokenType>,
    private val operatorChar: Char,
) : TokenizeStrategy {
    override fun lex(
        input: InputStream,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val startColumn = column
        val type = operators[operatorChar] ?: TokenType.ILLEGAL
        input.read() // Advance the input stream
        return Pair(Token(type, operatorChar.toString(), Position(line, startColumn)), column + 1)
    }
}