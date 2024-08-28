package lexer

import token.Position
import token.Token
import token.TokenType

class TokenIterator(private val input: String) : Iterator<Token> {
    private val keywords: Map<String, TokenType> =
        mapOf(
            "let" to TokenType.LET_KEYWORD,
            "println" to TokenType.PRINT,
        )
    private val types: Map<String, TokenType> = mapOf("number" to TokenType.NUMBER_TYPE, "string" to TokenType.STRING_TYPE)
    private val operators: Map<Char, TokenType> =
        mapOf(
            '+' to TokenType.SUM,
            '-' to TokenType.SUB,
            '*' to TokenType.MUL,
            '/' to TokenType.DIV,
            '=' to TokenType.ASSIGNATION,
        )

    private var i = 0
    private var line = 1
    private var column = 0

    override fun hasNext(): Boolean {
        while (i < input.length && input[i].isWhitespace()) {
            if (input[i] == '\n') {
                line++
                column = 0
            } else {
                column++
            }
            i++
        }
        return i <= input.length
    }

    override fun next(): Token {
        while (i < input.length && input[i].isWhitespace()) {
            if (input[i] == '\n') {
                line++
                column = 0
            } else {
                column++
            }
            i++
        }

        if (i == input.length) {
            i++ // Move past the end to prevent repeated EOF tokens
            return Token(TokenType.EOF, "", Position(line, column))
        }

        val ch = input[i]
        return when {
            ch in 'a'..'z' || ch in 'A'..'Z' || ch == '_' -> {
                val start = i
                val startColumn = column
                while (i < input.length && (input[i] in 'a'..'z' || input[i] in 'A'..'Z' || input[i] == '_')) {
                    i++
                    column++
                }
                val word = input.substring(start, i)
                val type = keywords[word] ?: types[word] ?: TokenType.IDENTIFIER
                Token(type, word, Position(line, startColumn))
            }
            ch in '0'..'9' -> {
                val start = i
                val startColumn = column
                while (i < input.length && (input[i] in '0'..'9' || input[i] == '.')) {
                    i++
                    column++
                }
                Token(TokenType.NUMBER_LITERAL, input.substring(start, i), Position(line, startColumn))
            }
            ch == '"' || ch == '\'' -> {
                val start = i
                val startColumn = column
                val quoteType = ch
                i++
                column++
                while (i < input.length && input[i] != quoteType) {
                    i++
                    column++
                }
                if (i < input.length) {
                    i++ // Closing quote
                    column++
                }
                Token(TokenType.STRING_LITERAL, input.substring(start, i), Position(line, startColumn))
            }
            ch in "+-*/" -> {
                val startColumn = column
                val type = operators[ch] ?: TokenType.ILLEGAL
                i++
                column++
                Token(type, ch.toString(), Position(line, startColumn))
            }
            ch == '=' -> {
                val startColumn = column
                i++
                column++
                Token(TokenType.ASSIGNATION, ch.toString(), Position(line, startColumn))
            }
            ch == ';' -> {
                val startColumn = column
                i++
                column++
                Token(TokenType.SEMICOLON, ch.toString(), Position(line, startColumn))
            }
            ch == ':' -> {
                val startColumn = column
                i++
                column++
                Token(TokenType.COLON, ch.toString(), Position(line, startColumn))
            }
            ch == '(' -> {
                val startColumn = column
                i++
                column++
                Token(TokenType.LEFT_PAR, ch.toString(), Position(line, startColumn))
            }
            ch == ')' -> {
                val startColumn = column
                i++
                column++
                Token(TokenType.RIGHT_PAR, ch.toString(), Position(line, startColumn))
            }
            else -> {
                val startColumn = column
                i++
                column++
                Token(TokenType.ILLEGAL, ch.toString(), Position(line, startColumn))
            }
        }
    }
}
