import lexer.TokenizeStrategyFactory
import source.SourceReader
import token.Position
import token.Token
import token.TokenMapSuite
import token.TokenType

class TokenIterator(private val input: SourceReader, private val tokens: TokenMapSuite, private val version: String) : Iterator<Token> {
    private val keywords: Map<String, TokenType> = tokens.keywords
    private val types: Map<String, TokenType> = tokens.types
    private val operators: Map<Char, TokenType> = tokens.operators
    private val symbols: Map<Char, TokenType> = tokens.symbols

    private val strategyFactory = TokenizeStrategyFactory(keywords, types, operators, symbols)

    private var line = 1
    private var column = 0

    override fun hasNext(): Boolean {
        while (input.hasMore() && input.current().isWhitespace()) {
            if (input.current() == '\n') {
                line++
                column = 0
            } else {
                column++
            }
            input.advance()
        }
        return input.hasMore()
    }

    override fun next(): Token {
        while (input.hasMore() && input.current().isWhitespace()) {
            if (input.current() == '\n') {
                line++
                column = 0
            } else {
                column++
            }
            input.advance()
        }

        if (!input.hasMore()) {
            return Token(TokenType.EOF, "", Position(line, column))
        }

        val ch = input.current()
        val strategy = strategyFactory.getStrategy(version, ch)
        val lexingResult = strategy.lex(input, line, column)
        column = lexingResult.second
        return lexingResult.first
    }
}
