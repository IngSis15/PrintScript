package lexer

import token.Token
import token.TokenMapSuite
import token.TokenType

class TokenIterator(val lexer: Lexer, tokens: TokenMapSuite, private val version: String) : Iterator<Token> {
    private val strategyFactory = TokenizeStrategyFactory(tokens)

    override fun hasNext(): Boolean {
        lexer.skipWhitespace()
        return lexer.hasMore()
    }

    override fun next(): Token {
        lexer.skipWhitespace()

        if (!lexer.hasMore()) {
            return Token(TokenType.EOF, "", lexer.pos())
        }

        val strategy = strategyFactory.getStrategy(version, lexer.current())
        val token = strategy.lex(lexer)
        return token
    }
}
