package lexer.tokenizeStrategies

import lexer.TokenizeStrategy
import token.Position
import token.Token
import token.TokenType
import java.io.InputStream

class NumberLiteralStrategy : TokenizeStrategy {
    override fun lex(
        input: InputStream,
        line: Int,
        column: Int,
    ): Pair<Token, Int> {
        val word = StringBuilder()
        val startColumn = column
        var col = column
        var currentChar = input.read()

        while (currentChar != -1 && (currentChar.toChar() in '0'..'9' || currentChar.toChar() == '.')) {
            word.append(currentChar.toChar())
            currentChar = input.read()
            col++
        }

        return Pair(Token(TokenType.NUMBER_LITERAL, word.toString(), Position(line, startColumn)), col)
    }
}