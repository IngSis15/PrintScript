package lexer.tokenizeStrategies

import lexer.Lexer
import lexer.TokenizeStrategy
import lib.Position
import token.Token
import token.TokenType

class SymbolStrategy(
    private val symbols: Map<Char, TokenType>,
    private val symbolsChar: Char,
) : TokenizeStrategy {
    override fun lex(lexer: Lexer): Token {
        val startColumn = lexer.posColumn()
        val type = symbols[symbolsChar] ?: TokenType.ILLEGAL
        lexer.advance()
        return Token(type, symbolsChar.toString(), Position(lexer.posLine(), startColumn))
    }
}
