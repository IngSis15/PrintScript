package org.example.ast

class PrintNode(val expression: ASTNode) : ASTNode {
    override fun getType(): String {
        return "Print"
    }
}