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
        var hasDecimalPoint = false

        while (lexer.current() in '0'..'9' || lexer.current() == '.') {
            if (lexer.current() == '.') {
                if (hasDecimalPoint) {
                    throw IllegalArgumentException("Malformed number: multiple decimal points")
                }
                hasDecimalPoint = true
            }
            word.append(lexer.current())
            lexer.advance()
        }

        if (hasDecimalPoint && word.last() == '.') {
            throw IllegalArgumentException("Malformed number: decimal point without digits")
        }

        return Token(TokenType.NUMBER_LITERAL, word.toString(), Position(lexer.posLine(), startColumn))
    }
}
