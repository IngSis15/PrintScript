package parser.semantic

data class SemanticResult(val success: Boolean, val message: String) {
    companion object {
        fun success() = SemanticResult(true, "")

        fun failure(message: String) = SemanticResult(false, message)
    }
}
