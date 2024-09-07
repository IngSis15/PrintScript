package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import token.Position
import token.Token
import token.TokenType
import java.io.InputStream

class KeywordStrategy(private val keywords: Map<String, TokenType>, private val types: Map<String, TokenType>) : TokenizeStrategy {
    override fun lex(
        input: InputStream,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val word = StringBuilder()
        val startColumn = column
        var col = column
        var currentChar = input.read()

        while (currentChar != -1 && (currentChar.toChar() in 'a'..'z' || currentChar.toChar() in 'A'..'Z' || currentChar.toChar() == '_')) {
            word.append(currentChar.toChar())
            currentChar = input.read()
            col++
        }

        val type = keywords[word.toString()] ?: types[word.toString()] ?: TokenType.IDENTIFIER
        return Pair(Token(type, word.toString(), Position(line, startColumn)), col)
    }
}