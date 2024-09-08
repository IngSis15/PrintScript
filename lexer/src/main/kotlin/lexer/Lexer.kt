package lexer

import TokenIterator
import token.Position
import token.Token
import token.TokenMapSuite
import token.TokenMapSuiteFactory
import java.io.InputStream

class Lexer(
    private val input: InputStream,
    private val version: String,
    private var position: Position = Position(1, 1),
) {
    private val tokenMap: TokenMapSuite = TokenMapSuiteFactory().createTokenMapSuite(version)
    private var currentChar: Int = input.read()

    fun lex(): Iterator<Token> {
        return TokenIterator(this, tokenMap, version)
    }

    fun advance() {
        currentChar = input.read()
        position = Position(position.line, position.column + 1)
    }

    fun hasMore(): Boolean {
        return currentChar != -1
    }

    fun skipWhitespace() {
        while (hasMore() && current().isWhitespace()) {
            position =
                if (current() == '\n') {
                    Position(position.line + 1, 0)
                } else {
                    Position(position.line, position.column)
                }
            advance()
        }
    }

    fun current(): Char {
        return currentChar.toChar()
    }

    fun posLine() = position.line

    fun posColumn() = position.column

    fun pos() = position
}
