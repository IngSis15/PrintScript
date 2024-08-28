package source

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.io.File

class SourceReaderTests {
    @Test
    fun `test read simple file`() {
        val resource = javaClass.classLoader.getResource("testfile.ps")
        val file = File(resource!!.toURI())

        val fileReader = FileReader(file)

        assertEquals('h', fileReader.current())
        fileReader.advance()
        assertEquals('e', fileReader.current())
        fileReader.advance()
        assertEquals('l', fileReader.current())
        fileReader.advance()
        assertEquals('l', fileReader.current())
        fileReader.advance()
        assertEquals('o', fileReader.current())
        fileReader.advance()

        assertFalse(fileReader.hasMore())

        fileReader.close()
    }

    @Test
    fun `test simple string reader`() {
        val stringReader = StringReader("hello")

        assertEquals('h', stringReader.current())
        stringReader.advance()
        assertEquals('e', stringReader.current())
        stringReader.advance()
        assertEquals('l', stringReader.current())
        stringReader.advance()
        assertEquals('l', stringReader.current())
        stringReader.advance()
        assertEquals('o', stringReader.current())
        stringReader.advance()

        assertFalse(stringReader.hasMore())
    }
}
