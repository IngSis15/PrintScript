package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import source.SourceReader
import token.Position
import token.Token
import token.TokenType

class NumberLiteralStrategy : TokenizeStrategy {
    override fun lex(
        input: SourceReader,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val word = StringBuilder()
        val startColumn = column
        var col = column
        while (input.hasMore() && (input.current() in '0'..'9' || input.current() == '.')) {
            word.append(input.current())
            input.advance()
            col++
        }
        return Pair(Token(TokenType.NUMBER_LITERAL, word.toString(), Position(line, startColumn)), col)
    }
}
