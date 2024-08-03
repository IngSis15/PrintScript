package org.example

class Token(val start : Int, val end : Int, val type : TokenType, val literal : Any) {
    override fun toString() : String {
        return "$type" + " " + "$literal" + " " + "$start" + " " + "$end"
    }
}