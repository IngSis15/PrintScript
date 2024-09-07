package lexer
import TokenIterator
import source.SourceReader
import token.Token
import token.TokenMapSuite
import token.TokenMapSuiteFactory

class Lexer(private val version: String) {
    private val tokenMap: TokenMapSuite = TokenMapSuiteFactory().createTokenMapSuite(version)

    fun lex(input: SourceReader): Iterator<Token> {
        return TokenIterator(input, tokenMap, version)
    }
}
