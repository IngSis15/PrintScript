package lexer.tokenizeStrategies

import lexer.Lexer
import lexer.TokenizeStrategy
import lib.Position
import token.Token
import token.TokenType

class NumberLiteralStrategy : TokenizeStrategy {
    override fun lex(lexer: Lexer): Token {
        val word = StringBuilder()
        val startColumn = lexer.posColumn()

        while (lexer.current() in '0'..'9' || lexer.current() == '.') {
            word.append(lexer.current())
            lexer.advance()
        }

        return Token(TokenType.NUMBER_LITERAL, word.toString(), Position(lexer.posLine(), startColumn))
    }
}
