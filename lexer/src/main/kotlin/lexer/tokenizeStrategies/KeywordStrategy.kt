package lexer.tokenizeStrategies

import lexer.Lexer
import lexer.TokenizeStrategy
import lib.Position
import token.Token
import token.TokenType

class KeywordStrategy(private val keywords: Map<String, TokenType>, private val types: Map<String, TokenType>) : TokenizeStrategy {
    override fun lex(lexer: Lexer): Token {
        val word = StringBuilder()
        val startColumn = lexer.posColumn()

        while (lexer.current() in 'a'..'z' || lexer.current() in 'A'..'Z' || lexer.current() == '_') {
            word.append(lexer.current())
            lexer.advance()
        }

        val type = keywords[word.toString()] ?: types[word.toString()] ?: TokenType.IDENTIFIER
        return Token(type, word.toString(), Position(lexer.posLine(), startColumn))
    }
}
