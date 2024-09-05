package source

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.Position
import token.Token
import token.TokenType

class TokenTranslatorTests {
    @Test
    fun `test token translator`() {
        val tokens =
            listOf(
                Token(TokenType.IDENTIFIER, "x", Position(0, 0)),
                Token(TokenType.WHITESPACE, " ", Position(0, 0)),
            ).iterator()

        val tokenTranslator = TokenTranslator(tokens)

        assertEquals(tokenTranslator.current(), 'x')
        tokenTranslator.advance()
        assertEquals(tokenTranslator.current(), ' ')
    }
}
