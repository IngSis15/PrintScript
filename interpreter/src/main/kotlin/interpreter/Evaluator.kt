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
import source.PrintEmitter

class Evaluator(private val printEmitter: PrintEmitter) : ExpressionVisitor<Any, Scope> {
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

        if (expr.left is IdentifierExpr) {
            val variableName = (expr.left as IdentifierExpr).name
            val variable =
                context.getVariable(variableName)
                    ?: throw IllegalArgumentException("Undefined variable: $variableName")

            val expectedType = variable.type
            val valueType =
                when (value) {
                    is Int -> "number"
                    is String -> "string"
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
        val variable = evaluate(expr.variable, context) as TypeExpr
        val value = evaluate(expr.value, context)

        val variableName = variable.name
        val variableType = variable.type
        context.setVariable(variableName, variableType, value)
        return value
    }

    override fun visit(
        expr: CallPrintExpr,
        context: Scope,
    ): Any {
        val valueToPrint = evaluate(expr.arg, context)
        printEmitter.print(valueToPrint.toString())
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
        return expr
    }

    override fun visit(
        expr: OperatorExpr,
        context: Scope,
    ): Any {
        val leftValue = evaluate(expr.left, context)
        val rightValue = evaluate(expr.right, context)

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
        } else if (leftValue is String && rightValue is String) {
            return when (expr.op) {
                "+" -> leftValue + rightValue
                else -> throw IllegalArgumentException("Unsupported operator for strings: ${expr.op}")
            }
        } else if (leftValue is String && rightValue is Number) {
            return when (expr.op) {
                "+" -> leftValue + rightValue.toString()
                else -> throw IllegalArgumentException("Unsupported operator for string and number: ${expr.op}")
            }
        } else if (leftValue is Number && rightValue is String) {
            return when (expr.op) {
                "+" -> leftValue.toString() + rightValue
                else -> throw IllegalArgumentException("Unsupported operator for number and string: ${expr.op}")
            }
        } else {
            throw IllegalArgumentException("Operands must be both numbers, both strings, or one string and one number")
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
}
