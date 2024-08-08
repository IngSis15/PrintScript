package org.example

class LexerImplementation {

    private val keywords = mapOf("let" to TokenType.LET_KEYWORD, "println" to TokenType.PRINT)
    private val types = mapOf("number" to TokenType.NUMBER_TYPE, "string" to TokenType.STRING_TYPE)
    private val operators = mapOf(
        '+' to TokenType.SUM,
        '-' to TokenType.SUB,
        '*' to TokenType.MUL,
        '/' to TokenType.DIV,
        '=' to TokenType.ASSIGNATION
    )

    fun lex(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var i = 0

        while (i < input.length) {
            when (val ch = input[i]) {
                in 'a'..'z', in 'A'..'Z', '_' -> {
                    val start = i
                    while (i < input.length && (input[i] in 'a'..'z' || input[i] in 'A'..'Z' || input[i] == '_')) i++
                    val word = input.substring(start, i)
                    val type = keywords[word] ?: types[word] ?: TokenType.IDENTIFIER
                    tokens.add(Token(type, word, start, i))
                }
                in '0'..'9' -> {
                    val start = i
                    while (i < input.length && (input[i] in '0'..'9' || input[i] == '.')) i++
                    tokens.add(Token(TokenType.NUMBER_LITERAL, input.substring(start, i), start, i))
                }
                '"', '\'' -> {
                    val start = i
                    val quoteType = ch
                    i++
                    while (i < input.length && input[i] != quoteType) i++
                    if (i < input.length) i++ // Closing quote
                    tokens.add(Token(TokenType.STRING_LITERAL, input.substring(start, i), start, i))
                }
                '+', '-', '*', '/' -> {
                    val start = i
                    val type = operators[ch] ?: TokenType.ILLEGAL
                    tokens.add(Token(type, ch.toString(), start, start + 1))
                    i++
                }
                '=' -> {
                    val start = i
                    tokens.add(Token(TokenType.ASSIGNATION, ch.toString(), start, start + 1))
                    i++
                }
                ';' -> {
                    val start = i
                    tokens.add(Token(TokenType.SEMICOLON, ch.toString(), start, start + 1))
                    i++
                }
                ':' -> {
                    val start = i
                    tokens.add(Token(TokenType.COLON, ch.toString(), start, start + 1))
                    i++
                }
                '(' -> {
                    val start = i
                    tokens.add(Token(TokenType.LEFT_PAR, ch.toString(), start, start + 1))
                    i++
                }
                ')' -> {
                    val start = i
                    tokens.add(Token(TokenType.RIGHT_PAR, ch.toString(), start, start + 1))
                    i++
                }
                ' ', '\t', '\n', '\r' -> i++ // Skip whitespace
                else -> {
                    val start = i
                    tokens.add(Token(TokenType.ILLEGAL, ch.toString(), start, start + 1))
                    i++
                }
            }
        }

        tokens.add(Token(TokenType.EOF, "", input.length, input.length))
        return tokens
    }
}
