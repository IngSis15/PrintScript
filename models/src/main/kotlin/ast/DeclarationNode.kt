package org.example.ast

class DeclarationNode(val identifier: IdentifierNode, val right: ASTNode, val start: Int, val end: Int) : ASTNode {
    override fun getType(): String {
        return "Declaration"
    }
}

