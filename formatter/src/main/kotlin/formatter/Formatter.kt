package formatter

import token.Token

class Formatter(private val config: FormatterConfig) {
    fun format(tokens: Iterator<Token>): Iterator<Token> {
        return FormatterTokenIterator(tokens, config)
    }
}
