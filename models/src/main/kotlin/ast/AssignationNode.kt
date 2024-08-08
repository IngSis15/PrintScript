package org.example.ast

class AssignationNode(val identifier: IdentifierNode, val right: ASTNode): ASTNode {
    override fun getType(): String {
        return "Assignation"
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other is AssignationNode) {
            return other.identifier == identifier && other.right == right
        }
        return false
    }
}