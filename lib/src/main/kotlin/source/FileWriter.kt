package source

import java.io.File
import java.io.FileWriter

class FileWriter(private val output: String) : Writer {
    override fun write(sourceReader: SourceReader) {
        val file = File(output)
        val fileWriter = FileWriter(file)

        while (sourceReader.hasMore()) {
            fileWriter.write(sourceReader.current().code)
            sourceReader.advance()
        }

        fileWriter.close()
    }
}
