package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import source.SourceReader
import token.Position
import token.Token
import token.TokenType

class SymbolStrategy(
    private val symbols: Map<Char, TokenType>,
    private val symbolsChar: Char,
) : TokenizeStrategy {
    override fun lex(
        input: SourceReader,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val startColumn = column
        val type = symbols[symbolsChar] ?: TokenType.ILLEGAL
        input.advance()
        return Pair(Token(type, symbolsChar.toString(), Position(line, startColumn)), column + 1)
    }
}
