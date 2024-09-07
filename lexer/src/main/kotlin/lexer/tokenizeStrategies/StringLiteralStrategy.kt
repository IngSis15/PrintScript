package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import token.Position
import token.Token
import token.TokenType
import java.io.InputStream

class StringLiteralStrategy(private val delimiter: Char) : TokenizeStrategy {
    override fun lex(
        input: InputStream,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val word = StringBuilder()
        val startColumn = column
        var col = column
        var currentChar = input.read()
        col++
        while (currentChar != -1 && currentChar.toChar() != delimiter) {
            word.append(currentChar.toChar())
            currentChar = input.read()
            col++
        }
        if (currentChar != -1) {
            currentChar = input.read()
            col++
        }
        return Pair(Token(TokenType.STRING_LITERAL, word.toString(), Position(line, startColumn)), col)
    }
}