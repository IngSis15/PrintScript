package lexer
import org.example.Token

class Lexer {
    fun lex(input: String): Iterator<Token> {
        return TokenIterator(input)
    }
}
