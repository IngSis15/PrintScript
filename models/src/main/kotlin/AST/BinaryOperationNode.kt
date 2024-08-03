package org.example.AST

class BinaryOperationNode(val left: ASTNode, val right: ASTNode, val start: Int, val end: Int, val operator: String) : ASTNode{

    override fun getType(): String {
        return "BinaryOperation"
    }
}