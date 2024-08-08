package org.example.ast

class BinaryOperationNode(val left: ASTNode, val right: ASTNode, val start: Int, val end: Int, val operator: String) : ASTNode{
    override fun getType(): String {
        return "BinaryOperation"
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other is BinaryOperationNode) {
            return  other.left == left &&
                    other.right == right &&
                    other.start == start &&
                    other.end == end &&
                    other.operator == operator
        }
        return false
    }
}