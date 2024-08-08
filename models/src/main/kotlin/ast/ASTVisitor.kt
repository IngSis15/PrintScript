package org.example.ast

interface ASTVisitor {
    fun visit(node: BinaryOperationNode)
    fun visit(node: DeclarationNode)
    fun visit(node: IdentifierNode)
    fun visit(node: LiteralNode)
    fun visit(node: PrintNode)
    fun visit(node: AssignationNode)
}