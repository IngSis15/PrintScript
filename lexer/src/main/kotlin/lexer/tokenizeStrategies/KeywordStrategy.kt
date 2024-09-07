package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import source.SourceReader
import token.Position
import token.Token
import token.TokenType

class KeywordStrategy(private val keywords: Map<String, TokenType>, private val types: Map<String, TokenType>) : TokenizeStrategy {
    override fun lex(
        input: SourceReader,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val word = StringBuilder()
        val startColumn = column
        var col = column
        while (input.hasMore() && (input.current() in 'a'..'z' || input.current() in 'A'..'Z' || input.current() == '_')) {
            word.append(input.current())
            input.advance()
            col++
        }
        val type = keywords[word.toString()] ?: types[word.toString()] ?: TokenType.IDENTIFIER
        return Pair(Token(type, word.toString(), Position(line, startColumn)), col)
    }
}
