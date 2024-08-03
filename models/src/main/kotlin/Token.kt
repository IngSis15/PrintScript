package org.example

class Token(val type : TokenType, val literal : String, val start : Int, val end : Int) {
    override fun toString() : String {
        return "[$type, $literal, $start, $end]"
    }
}