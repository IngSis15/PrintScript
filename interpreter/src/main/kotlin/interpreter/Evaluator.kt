package interpreter

import ast.AssignExpr
import ast.CallPrintExpr
import ast.DeclareExpr
import ast.Expression
import ast.ExpressionVisitor
import ast.IdentifierExpr
import ast.NumberExpr
import ast.OperatorExpr
import ast.StringExpr
import ast.TypeExpr
import ast.VariableExpr

class Evaluator : ExpressionVisitor<Any, Scope> {
    fun evaluate(
        expression: Expression,
        scope: Scope,
    ): Any {
        return expression.accept(this, scope)
    }

    override fun visit(
        expr: AssignExpr,
        context: Scope,
    ): Any {
        val value = evaluate(expr.value, context)

        if (expr.left is VariableExpr) {
            val variableName = (expr.left as VariableExpr).name
            val variable =
                context.getVariable(variableName)
                    ?: throw IllegalArgumentException("Undefined variable: $variableName")

            val expectedType = variable.type
            val valueType =
                when (value) {
                    is Int -> "Integer"
                    is Double -> "Double"
                    is String -> "String"
                    else -> throw IllegalArgumentException("Unsupported value type: ${value::class.simpleName}")
                }

            if (expectedType != valueType) {
                throw IllegalArgumentException("Type mismatch: expected $expectedType, but found $valueType")
            }

            context.setVariable(variableName, expectedType, value)
            return value
        } else {
            throw IllegalArgumentException("Expected a variable on the left-hand side of the assignment")
        }
    }

    override fun visit(
        expr: DeclareExpr,
        context: Scope,
    ): Any {
        val value = evaluate(expr.value, context)

        val variableName = (expr.variable as TypeExpr).name
        val variableType = (expr.variable as TypeExpr).type
        context.setVariable(variableName, variableType, value)
        return value
    }

    override fun visit(
        expr: CallPrintExpr,
        context: Scope,
    ): Any {
        val valueToPrint = evaluate(expr.arg, context)
        println(valueToPrint)
        return valueToPrint
    }

    override fun visit(
        expr: IdentifierExpr,
        context: Scope,
    ): Any {
        val variable = context.getVariable(expr.name)

        if (variable != null) {
            return variable.value
        } else {
            throw IllegalArgumentException("Undefined variable: ${expr.name}")
        }
    }

    override fun visit(
        expr: TypeExpr,
        context: Scope,
    ): Any {
        val variable = context.getVariable(expr.name)

        if (variable != null) {
            val actualType = variable.type

            if (actualType == expr.type) {
                return variable
            } else {
                throw IllegalArgumentException("Type mismatch: expected ${expr.type}, but found $actualType")
            }
        } else {
            throw IllegalArgumentException("Undefined variable: ${expr.name}")
        }
    }

    override fun visit(
        expr: OperatorExpr,
        context: Scope,
    ): Any {
        val leftValue = expr.left.accept(this, context)
        val rightValue = expr.right.accept(this, context)

        if (leftValue is Number && rightValue is Number) {
            return when (expr.op) {
                "+" -> leftValue.toDouble() + rightValue.toDouble()
                "-" -> leftValue.toDouble() - rightValue.toDouble()
                "*" -> leftValue.toDouble() * rightValue.toDouble()
                "/" ->
                    if (rightValue.toDouble() != 0.0) {
                        leftValue.toDouble() / rightValue.toDouble()
                    } else {
                        throw ArithmeticException("Division by zero")
                    }
                else -> throw IllegalArgumentException("Unsupported operator: ${expr.op}")
            }
        } else {
            throw IllegalArgumentException("Operands must be numbers")
        }
    }

    override fun visit(
        expr: NumberExpr,
        context: Scope,
    ): Any {
        return expr.value
    }

    override fun visit(
        expr: StringExpr,
        context: Scope,
    ): Any {
        return expr.value
    }

    override fun visit(
        expr: VariableExpr,
        context: Scope,
    ): Any {
        val variableValue = context.getVariable(expr.name)

        if (variableValue != null) {
            return variableValue.value
        } else {
            throw IllegalArgumentException("Undefined variable: ${expr.name}")
        }
    }
}
