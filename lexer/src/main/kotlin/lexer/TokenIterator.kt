package lexer

import source.SourceReader
import token.Position
import token.Token
import token.TokenType

class TokenIterator(private val input: SourceReader) : Iterator<Token> {
    private val keywords: Map<String, TokenType> =
        mapOf(
            "let" to TokenType.LET_KEYWORD,
            "println" to TokenType.PRINT,
        )
    private val types: Map<String, TokenType> =
        mapOf("number" to TokenType.NUMBER_TYPE, "string" to TokenType.STRING_TYPE)
    private val operators: Map<Char, TokenType> =
        mapOf(
            '+' to TokenType.SUM,
            '-' to TokenType.SUB,
            '*' to TokenType.MUL,
            '/' to TokenType.DIV,
            '=' to TokenType.ASSIGNATION,
        )

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

        return when (val ch = input.current()) {
            in 'a'..'z', in 'A'..'Z', '_' -> {
                val word = StringBuilder()
                val startColumn = column
                while (input.hasMore() && (input.current() in 'a'..'z' || input.current() in 'A'..'Z' || input.current() == '_')) {
                    word.append(input.current())
                    input.advance()
                    column++
                }
                val type = keywords[word.toString()] ?: types[word.toString()] ?: TokenType.IDENTIFIER
                Token(type, word.toString(), Position(line, startColumn))
            }

            in '0'..'9' -> {
                val word = StringBuilder()
                val startColumn = column
                while (input.hasMore() && (input.current() in '0'..'9' || input.current() == '.')) {
                    word.append(input.current())
                    input.advance()
                    column++
                }
                Token(TokenType.NUMBER_LITERAL, word.toString(), Position(line, startColumn))
            }

            '"', '\'' -> {
                val word = StringBuilder()
                val startColumn = column
                input.advance()
                column++
                while (input.hasMore() && input.current() != ch) {
                    word.append(input.current())
                    input.advance()
                    column++
                }
                if (input.hasMore()) {
                    input.advance()
                    column++
                }
                Token(TokenType.STRING_LITERAL, word.toString(), Position(line, startColumn))
            }

            in "+-*/" -> {
                val startColumn = column
                val type = operators[ch] ?: TokenType.ILLEGAL
                input.advance()
                column++
                Token(type, ch.toString(), Position(line, startColumn))
            }

            '=' -> {
                val startColumn = column
                input.advance()
                column++
                Token(TokenType.ASSIGNATION, ch.toString(), Position(line, startColumn))
            }

            ';' -> {
                val startColumn = column
                input.advance()
                column++
                Token(TokenType.SEMICOLON, ch.toString(), Position(line, startColumn))
            }

            ':' -> {
                val startColumn = column
                input.advance()
                column++
                Token(TokenType.COLON, ch.toString(), Position(line, startColumn))
            }

            '(' -> {
                val startColumn = column
                input.advance()
                column++
                Token(TokenType.LEFT_PAR, ch.toString(), Position(line, startColumn))
            }

            ')' -> {
                val startColumn = column
                input.advance()
                column++
                Token(TokenType.RIGHT_PAR, ch.toString(), Position(line, startColumn))
            }

            else -> {
                val startColumn = column
                input.advance()
                column++
                Token(TokenType.ILLEGAL, ch.toString(), Position(line, startColumn))
            }
        }
    }
}
