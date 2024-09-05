package formatter

import token.Token
import token.TokenType

class FormatterTokenIterator(
    private val tokens: Iterator<Token>,
    private val config: FormatterConfig,
) : Iterator<Token> {
    private var nextTokens: MutableList<Token> = mutableListOf()

    override fun hasNext(): Boolean {
        return nextTokens.isNotEmpty() || tokens.hasNext()
    }

    override fun next(): Token {
        if (nextTokens.isNotEmpty()) {
            return nextTokens.removeAt(0)
        }

        val token = tokens.next()
        when (token.type) {
            TokenType.COLON -> formatColon(token)
            TokenType.ASSIGNATION -> formatAssignation(token)
            TokenType.PRINT -> formatPrint(token)
            TokenType.SEMICOLON -> formatSemicolon(token)
            TokenType.SUM, TokenType.SUB, TokenType.MUL, TokenType.DIV -> formatOperator(token)
            TokenType.WHITESPACE -> formatWhitespace(token)
            TokenType.STRING_LITERAL -> formatQuotes(token)
            else -> return token
        }

        return nextTokens.removeAt(0)
    }

    private fun formatColon(token: Token) {
        if (config.spaceBeforeColon) {
            nextTokens.add(Token(TokenType.WHITESPACE, " ", token.start))
        }
        nextTokens.add(token)
        if (config.spaceAfterColon) {
            nextTokens.add(Token(TokenType.WHITESPACE, " ", token.start))
        }
    }

    private fun formatAssignation(token: Token) {
        if (config.spaceAroundAssignment) {
            nextTokens.add(Token(TokenType.WHITESPACE, " ", token.start))
        }
        nextTokens.add(token)
        if (config.spaceAroundAssignment) {
            nextTokens.add(Token(TokenType.WHITESPACE, " ", token.start))
        }
    }

    private fun formatPrint(token: Token) {
        repeat(config.newLinesBeforePrintln) {
            nextTokens.add(Token(TokenType.LINEBREAK, "\n", token.start))
        }
        nextTokens.add(token)
    }

    private fun formatSemicolon(token: Token) {
        nextTokens.add(token)
        nextTokens.add(Token(TokenType.LINEBREAK, "\n", token.start))
    }

    private fun formatOperator(token: Token) {
        nextTokens.add(Token(TokenType.WHITESPACE, " ", token.start))
        nextTokens.add(token)
        nextTokens.add(Token(TokenType.WHITESPACE, " ", token.start))
    }

    private fun formatWhitespace(token: Token) {
        if (nextTokens.isNotEmpty() && nextTokens.last().type == TokenType.WHITESPACE) {
            return
        }
        nextTokens.add(token)
    }

    private fun formatQuotes(token: Token) {
        nextTokens.add(Token(TokenType.STRING_LITERAL, "\"${token.literal}\"", token.start))
    }
}
