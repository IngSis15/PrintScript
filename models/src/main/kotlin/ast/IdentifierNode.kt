package org.example.ast

class IdentifierNode(val type: Type, val name : String, val start: Int, val end: Int) : ASTNode{
    override fun getType(): String {
        return "Identifier"
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visit(this)
    }
}

