package runner.utils

import source.SourceReader
import source.Writer

class StringWriter() : Writer {
    private val value = StringBuilder()

    override fun write(sourceReader: SourceReader) {
        while (sourceReader.hasMore()) {
            value.append(sourceReader.current())
            sourceReader.advance()
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}
