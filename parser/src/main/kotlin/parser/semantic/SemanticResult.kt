package parser.semantic

import lib.Position

data class SemanticResult(val success: Boolean, val message: String, val position: Position) {
    companion object {
        fun success() = SemanticResult(true, "", Position(0, 0))

        fun failure(
            message: String,
            position: Position,
        ) = SemanticResult(false, message, position)
    }
}
