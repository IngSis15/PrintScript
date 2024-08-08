package org.example.ast

class AssignationNode(val identifier: IdentifierNode, val right: ASTNode): ASTNode {
    override fun getType(): String {
        return "Assignation"
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }
}