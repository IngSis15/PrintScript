package source

import java.io.File
import java.io.Reader

class FileReader(file: File) : SourceReader {
    private val reader: Reader = file.reader()
    private var currentChar: Int = reader.read()

    override fun current(): Char {
        return if (currentChar != -1) currentChar.toChar() else Char.MIN_VALUE
    }

    override fun advance() {
        currentChar = reader.read()
    }

    override fun hasMore(): Boolean {
        return currentChar != -1
    }

    fun close() {
        reader.close()
    }
}
