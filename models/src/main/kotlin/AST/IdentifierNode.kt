package org.example.AST

class IdentifierNode(val type: Type, val name : String, val start: Int, val end: Int) : ASTNode{
    override fun getType(): String {
        return "Identifier"
    }
}

