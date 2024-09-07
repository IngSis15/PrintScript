package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import token.Position
import token.Token
import token.TokenType
import java.io.InputStream

class SymbolStrategy(
    private val symbols: Map<Char, TokenType>,
    private val symbolsChar: Char,
) : TokenizeStrategy {
    override fun lex(
        input: InputStream,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val startColumn = column
        val type = symbols[symbolsChar] ?: TokenType.ILLEGAL
        input.read() // Advance the input stream
        return Pair(Token(type, symbolsChar.toString(), Position(line, startColumn)), column + 1)
    }
}