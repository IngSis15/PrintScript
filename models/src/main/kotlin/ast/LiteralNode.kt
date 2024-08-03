package org.example.ast

class LiteralNode(val value: String, val type: Type): ASTNode {
    override fun getType(): String {
        return "Literal"
    }
}