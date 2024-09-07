import lexer.TokenizeStrategyFactory
import token.Position
import token.Token
import token.TokenMapSuite
import token.TokenType
import java.io.InputStream

class TokenIterator(private val input: InputStream, private val tokens: TokenMapSuite, private val version: String) : Iterator<Token> {
    private val keywords: Map<String, TokenType> = tokens.keywords
    private val types: Map<String, TokenType> = tokens.types
    private val operators: Map<Char, TokenType> = tokens.operators
    private val symbols: Map<Char, TokenType> = tokens.symbols

    private val strategyFactory = TokenizeStrategyFactory(keywords, types, operators, symbols)

    private var line = 1
    private var column = 1
    private var currentChar: Int = input.read()

    private fun advance() {
        currentChar = input.read()
        column++
    }

    private fun hasMore(): Boolean {
        return currentChar != -1
    }

    private fun current(): Char {
        return currentChar.toChar()
    }

    override fun hasNext(): Boolean {
        while (hasMore() && current().isWhitespace()) {
            if (current() == '\n') {
                line++
                column = 1
            } else {
                column++
            }
            advance()
        }
        return hasMore()
    }

    override fun next(): Token {
        while (hasMore() && current().isWhitespace()) {
            if (current() == '\n') {
                line++
                column = 1
            } else {
                column++
            }
            advance()
        }

        if (!hasMore()) {
            return Token(TokenType.EOF, "", Position(line, column))
        }

        val ch = current()
        val strategy = strategyFactory.getStrategy(version, ch)
        val lexingResult = strategy.lex(input, line, column)
        column = lexingResult.second
        advance()
        return lexingResult.first
    }
}