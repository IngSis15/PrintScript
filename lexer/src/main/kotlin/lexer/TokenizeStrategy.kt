package lexer

import token.Token

interface TokenizeStrategy {
    fun lex(lexer: Lexer): Token
}
