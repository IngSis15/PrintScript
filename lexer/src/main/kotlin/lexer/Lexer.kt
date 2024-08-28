package lexer
import token.Token

class Lexer {
    fun lex(input: String): Iterator<Token> {
        return TokenIterator(input)
    }
}
