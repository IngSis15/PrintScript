package lexer.tokenizeStrategies

import lexer.Lexer
import lexer.TokenizeStrategy
import token.Position
import token.Token
import token.TokenType

class StringLiteralStrategy(private val delimiter: Char) : TokenizeStrategy {
    override fun lex(lexer: Lexer): Token {
        val word = StringBuilder()
        val startColumn = lexer.posColumn()
        lexer.advance()
        while (lexer.current() != delimiter) {
            word.append(lexer.current())
            lexer.advance()
        }
        lexer.advance()
        return Token(TokenType.STRING_LITERAL, word.toString(), Position(lexer.posLine(), startColumn))
    }
}
