package org.example.ast

class DeclarationNode(val identifier: IdentifierNode, val right: ASTNode, val start: Int, val end: Int) : ASTNode {
    override fun getType(): String {
        return "Declaration"
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other is DeclarationNode) {
            return other.identifier == identifier && other.right == right && other.start == start && other.end == end
        }
        return false
    }
}

