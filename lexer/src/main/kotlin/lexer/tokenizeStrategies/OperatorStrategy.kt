package lexer.tokenizeStrategies

import lexer.Lexer
import lexer.TokenizeStrategy
import lib.Position
import token.Token
import token.TokenType

class OperatorStrategy(
    private val operators: Map<Char, TokenType>,
    private val operatorChar: Char,
) : TokenizeStrategy {
    override fun lex(lexer: Lexer): Token {
        val startColumn = lexer.posColumn()
        val type = operators[operatorChar] ?: TokenType.ILLEGAL
        lexer.advance()
        return Token(type, operatorChar.toString(), Position(lexer.posLine(), startColumn))
    }
}
