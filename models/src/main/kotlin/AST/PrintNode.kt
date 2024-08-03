package org.example.AST

class PrintNode(val expression: ASTNode) : ASTNode {
    override fun getType(): String {
        return "Print"
    }
}