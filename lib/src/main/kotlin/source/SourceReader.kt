package source

interface SourceReader {
    fun current(): Char

    fun advance()

    fun hasMore(): Boolean
}
