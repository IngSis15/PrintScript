package org.example.ast

class LiteralNode(val value: String, val type: Type): ASTNode {
    override fun getType(): String {
        return "Literal"
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other is LiteralNode) {
            return other.value == value && other.type == type
        }
        return false
    }
}