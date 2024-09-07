package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import source.SourceReader
import token.Position
import token.Token
import token.TokenType

class StringLiteralStrategy(private val delimiter: Char) : TokenizeStrategy {
    override fun lex(
        input: SourceReader,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val word = StringBuilder()
        val startColumn = column
        var col = column
        input.advance()
        col++
        while (input.hasMore() && input.current() != delimiter) {
            word.append(input.current())
            input.advance()
            col++
        }
        if (input.hasMore()) {
            input.advance()
            col++
        }
        return Pair(Token(TokenType.STRING_LITERAL, word.toString(), Position(line, startColumn)), col)
    }
}
