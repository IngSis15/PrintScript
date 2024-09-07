package lexer
import TokenIterator
import source.SourceReader
import token.Token
import token.TokenMapSuite
import token.TokenMapSuiteFactory
import java.io.InputStream

class Lexer(private val version: String) {
    private val tokenMap: TokenMapSuite = TokenMapSuiteFactory().createTokenMapSuite(version)

    fun lex(input: InputStream): Iterator<Token> {
        return TokenIterator(input, tokenMap, version)
    }
}
