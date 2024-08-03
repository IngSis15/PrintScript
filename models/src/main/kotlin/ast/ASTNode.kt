package org.example.ast

interface ASTNode {
    fun getType() : String
    fun accept(visitor: ASTVisitor)
}