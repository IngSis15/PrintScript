package lib

import token.Token
import java.io.Writer

class TokenWriter(private val tokens: Iterator<Token>, private val writer: Writer) {
    fun write() {
        while (tokens.hasNext()) {
            writer.write(tokens.next().literal)
        }
    }
}
