package lexer

import source.SourceReader
import token.Token

interface TokenizeStrategy {
    fun lex(
        input: SourceReader,
        line: Int,
        column: Int,
    ): Pair<Token, Int>
}
