package org.example

class Token(val type : TokenType, val literal : String, val start : Int, val end : Int) {
    override fun toString() : String {
        return "[$type, $literal, $start, $end]"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Token) return false
        return type == other.type && literal == other.literal && start == other.start && end == other.end
    }

    override fun hashCode(): Int {
        return type.hashCode() * 31 + literal.hashCode() * 31 + start * 31 + end
    }
}