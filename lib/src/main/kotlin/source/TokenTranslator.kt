package source

import token.Token

class TokenTranslator(private val tokens: Iterator<Token>) : SourceReader {
    private var currentToken = tokens.next()
    private var index = 0

    override fun current(): Char {
        if (index < currentToken.literal.length) {
            return currentToken.literal[index]
        }

        currentToken = tokens.next()
        index = 0
        return currentToken.literal[index]
    }

    override fun advance() {
        index++
    }

    override fun hasMore(): Boolean {
        return tokens.hasNext() || index < currentToken.literal.length
    }
}
