package formatter

import token.Token

class TokenStringBuilder(private val tokens: Iterator<Token>) {
    fun buildString(): String {
        val sb = StringBuilder()
        while (tokens.hasNext()) {
            sb.append(tokens.next().literal)
        }
        return sb.toString()
    }
}
