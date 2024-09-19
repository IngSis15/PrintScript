package parser.semantic

import ast.AssignExpr
import ast.BooleanExpr
import ast.CallPrintExpr
import ast.ConditionalExpr
import ast.DeclareExpr
import ast.Expression
import ast.ExpressionVisitor
import ast.IdentifierExpr
import ast.NumberExpr
import ast.OperatorExpr
import ast.ReadEnvExpr
import ast.ReadInputExpr
import ast.StringExpr
import parser.exception.SemanticException

class TypeVisitor : ExpressionVisitor<VariableType?, SymbolMap> {
    fun getType(
        expr: Expression,
        context: SymbolMap,
    ): VariableType? {
        return expr.accept(this, context)
    }

    override fun visit(
        expr: AssignExpr,
        context: SymbolMap,
    ): VariableType? {
        return null
    }

    override fun visit(
        expr: DeclareExpr,
        context: SymbolMap,
    ): VariableType {
        return VariableType.fromString(expr.type)
    }

    override fun visit(
        expr: CallPrintExpr,
        context: SymbolMap,
    ): VariableType? {
        return null
    }

    override fun visit(
        expr: IdentifierExpr,
        context: SymbolMap,
    ): VariableType? {
        return context.getSymbol(expr.name)?.type
    }

    override fun visit(
        expr: OperatorExpr,
        context: SymbolMap,
    ): VariableType? {
        val leftType = expr.left.accept(this, context)
        val rightType = expr.right.accept(this, context)

        return when (expr.op) {
            "+" -> {
                if (leftType == VariableType.STRING || rightType == VariableType.STRING) {
                    VariableType.STRING
                } else {
                    VariableType.NUMBER
                }
            }
            else -> {
                if (leftType == rightType) {
                    leftType
                } else {
                    throw SemanticException("Cannot perform operation ${expr.op} on types $leftType and $rightType", expr.pos)
                }
            }
        }
    }

    override fun visit(
        expr: NumberExpr,
        context: SymbolMap,
    ): VariableType {
        return VariableType.NUMBER
    }

    override fun visit(
        expr: StringExpr,
        context: SymbolMap,
    ): VariableType {
        return VariableType.STRING
    }

    override fun visit(
        expr: BooleanExpr,
        context: SymbolMap,
    ): VariableType {
        return VariableType.BOOLEAN
    }

    override fun visit(
        expr: ReadEnvExpr,
        context: SymbolMap,
    ): VariableType {
        return VariableType.ANY
    }

    override fun visit(
        expr: ConditionalExpr,
        context: SymbolMap,
    ): VariableType? {
        return null
    }

    override fun visit(
        expr: ReadInputExpr,
        context: SymbolMap,
    ): VariableType {
        return VariableType.ANY
    }
}
