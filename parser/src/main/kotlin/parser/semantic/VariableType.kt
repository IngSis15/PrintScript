package parser.semantic

enum class VariableType {
    NUMBER,
    STRING,
    ANY,
    BOOLEAN,
    ;

    companion object {
        fun fromString(type: String): VariableType {
            return when (type) {
                "number" -> NUMBER
                "string" -> STRING
                "boolean" -> BOOLEAN
                else -> throw IllegalArgumentException("Invalid type: $type")
            }
        }
    }
}
