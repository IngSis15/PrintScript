package lexer
import source.SourceReader
import token.Token

class Lexer {
    fun lex(input: SourceReader): Iterator<Token> {
        return TokenIterator(input)
    }
}
