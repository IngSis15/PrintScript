package interpreter

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
import interpreter.exception.EvaluatorException
import lib.InputProvider
import lib.PrintEmitter

class Evaluator(private val printEmitter: PrintEmitter, private val inputProvider: InputProvider) :
    ExpressionVisitor<Any, Scope> {
    fun evaluate(
        expression: Expression,
        scope: Scope,
    ): Any {
        return expression.accept(this, scope)
    }

    private fun numberToString(number: Number): String {
        return if (number.toDouble() % 1 == 0.0) {
            number.toInt().toString()
        } else {
            number.toString()
        }
    }

    private fun matchTypes(
        type: String,
        value: Any,
    ): Any {
        return when (type) {
            "number" ->
                value.toString().toDoubleOrNull()
                    ?: throw IllegalArgumentException("Value cannot be converted to a number: $value")

            "string" -> value.toString()
            "boolean" ->
                when (value) {
                    is Boolean -> value
                    is String -> value.equals("true", ignoreCase = true) || value.equals("false", ignoreCase = true)
                    is Number -> value != 0
                    else -> throw IllegalArgumentException("Value cannot be converted to a boolean: $value")
                }

            else -> throw IllegalArgumentException("Unsupported value type: ${value::class.simpleName}")
        }
    }

    override fun visit(
        expr: AssignExpr,
        context: Scope,
    ): Any {
        val value = evaluate(expr.value, context)

        if (expr.left !is IdentifierExpr) {
            throw EvaluatorException("Expected variable at ${expr.pos.line}:${expr.pos.column}")
        }

        val variableName = (expr.left as IdentifierExpr).name
        val variable =
            context.getVariable(variableName)
                ?: throw IllegalArgumentException("Undefined variable: $variableName")

        if (!variable.mutable) {
            throw EvaluatorException("Variable is not mutable: <$variableName> at ${expr.pos.line}:${expr.pos.column}")
        }

        val expectedType = variable.type

        matchTypes(expectedType, value)

        context.setVariable(variableName, expectedType, true, value)
        return value
    }

    override fun visit(
        expr: DeclareExpr,
        context: Scope,
    ): Any {
        val value = expr.value?.let { evaluate(it, context) }
        val variableName = expr.name
        val variableType = expr.type

        if (context.getVariable(variableName) != null) {
            throw EvaluatorException("Variable already declared: <$variableName> at ${expr.pos.line}:${expr.pos.column}")
        }

        val newValue = matchTypes(variableType, value ?: throw EvaluatorException("Variable must be initialized"))

        context.setVariable(variableName, variableType, expr.mutable, newValue)
        return variableName
    }

    override fun visit(
        expr: CallPrintExpr,
        context: Scope,
    ): Any {
        val valueToPrint = evaluate(expr.arg, context)

        val formattedValue =
            when (valueToPrint) {
                is Number -> numberToString(valueToPrint)
                else -> valueToPrint
            }

        printEmitter.print(formattedValue.toString())
        return valueToPrint
    }

    override fun visit(
        expr: IdentifierExpr,
        context: Scope,
    ): Any {
        val variable = context.getVariable(expr.name)

        if (variable != null) {
            if (variable.value != null) {
                return variable.value
            } else {
                throw EvaluatorException("Variable not initialized: <${expr.name}> at ${expr.pos.line}:${expr.pos.column}")
            }
        }
        throw EvaluatorException("Undefined variable: <${expr.name}> at ${expr.pos.line}:${expr.pos.column}")
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

                else -> throw EvaluatorException("Unsupported operator: <${expr.op}> at ${expr.pos.line}:${expr.pos.column}")
            }
        } else if (leftValue is String && rightValue is String) {
            return when (expr.op) {
                "+" -> leftValue + rightValue
                else -> throw IllegalArgumentException("Unsupported operator for strings: ${expr.op}")
            }
        } else if (leftValue is String && rightValue is Number) {
            return when (expr.op) {
                "+" -> leftValue + numberToString(rightValue)
                else -> throw IllegalArgumentException("Unsupported operator for string and number: ${expr.op}")
            }
        } else if (leftValue is Number && rightValue is String) {
            return when (expr.op) {
                "+" -> numberToString(leftValue) + rightValue
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

    override fun visit(
        expr: ReadEnvExpr,
        context: Scope,
    ): Any {
        val envName = evaluate(expr.name, context) as String
        val envValue = System.getenv(envName)
        return envValue
            ?: throw EvaluatorException("Environment variable not found: <$envName> at ${expr.pos.line}:${expr.pos.column}")
    }

    override fun visit(
        expr: ConditionalExpr,
        context: Scope,
    ): Any {
        val condition = evaluate(expr.condition, context)
        val isTrue = condition as Boolean

        if (isTrue) {
            expr.body.forEach { evaluate(it, Scope(context)) }
        } else {
            expr.elseBody.forEach { evaluate(it, Scope(context)) }
        }

        return isTrue
    }

    override fun visit(
        expr: ReadInputExpr,
        context: Scope,
    ): Any {
        val input = inputProvider.input()
        return input
    }

    override fun visit(
        expr: BooleanExpr,
        context: Scope,
    ): Any {
        return expr.value
    }
}
