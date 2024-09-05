package source

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class FileWriterTest {
    @Test
    fun `test file writer`() {
        File("src/test/resources/file-writer/test.txt").delete()
        val fileWriter = FileWriter("src/test/resources/file-writer/test.txt")
        fileWriter.write(StringReader("test"))

        val writtenContent = File("src/test/resources/file-writer/test.txt").readText()
        assertEquals("test", writtenContent)
    }
}
