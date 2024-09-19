package parser.exception

import lib.Position

class SemanticException(message: String) : Exception(message) {
    constructor(message: String, position: Position) : this("$message at position $position")
}
