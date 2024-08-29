package source

class StringReader(private val content: String) : SourceReader {
    private var index = 0

    override fun current(): Char {
        return if (index < content.length) content[index] else Char.MIN_VALUE
    }

    override fun advance() {
        if (index < content.length) {
            index++
        }
    }

    override fun hasMore(): Boolean {
        return index < content.length
    }
}
