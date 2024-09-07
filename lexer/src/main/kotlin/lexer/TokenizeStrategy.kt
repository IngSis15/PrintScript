package lexer

import source.SourceReader
import token.Token
import java.io.InputStream

interface TokenizeStrategy {
    fun lex(
        input: InputStream,
        line: Int,
        column: Int,
    ): Pair<Token, Int>
}
