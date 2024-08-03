package org.example.AST

class AssignationNode(val identifier: IdentifierNode, val right: ASTNode ): ASTNode {
    override fun getType(): String {
        return "Assignation"
    }
}