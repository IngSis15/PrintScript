package lib

data class Position(val line: Int, val column: Int) {
    override fun toString(): String {
        return "($line, $column)"
    }
}
