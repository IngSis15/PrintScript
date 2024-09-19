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
import interpreter.strategies.AdditionStrategy
import interpreter.strategies.DivisionStrategy
import interpreter.strategies.MultiplicationStrategy
import interpreter.strategies.SubtractionStrategy
import lib.InputProvider
import lib.PrintEmitter

class Evaluator(private val printEmitter: PrintEmitter, private val inputProvider: InputProvider) :
    ExpressionVisitor<Any, Scope> {
    private val operatorStrategies =
        mapOf(
            "+" to AdditionStrategy(),
            "-" to SubtractionStrategy(),
            "*" to MultiplicationStrategy(),
            "/" to DivisionStrategy(),
        )

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

        val newValue = value?.let { matchTypes(variableType, it) }
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
        val variable =
            context.getVariable(expr.name)
                ?: throw EvaluatorException("Undefined variable: <${expr.name}> at ${expr.pos.line}:${expr.pos.column}")

        return variable.value ?: throw EvaluatorException("Variable not initialized: <${expr.name}> at ${expr.pos.line}:${expr.pos.column}")
    }

    override fun visit(
        expr: OperatorExpr,
        context: Scope,
    ): Any {
        val leftValue = evaluate(expr.left, context)
        val rightValue = evaluate(expr.right, context)

        val strategy =
            operatorStrategies[expr.op]
                ?: throw EvaluatorException("Unsupported operator: ${expr.op}")

        return strategy.execute(leftValue, rightValue)
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
        return System.getenv(envName) ?: throw EvaluatorException("Environment variable not found: <$envName>")
    }

    override fun visit(
        expr: ConditionalExpr,
        context: Scope,
    ): Any {
        val condition = evaluate(expr.condition, context) as Boolean
        if (condition) {
            expr.body.forEach { evaluate(it, Scope(context)) }
        } else {
            expr.elseBody.forEach { evaluate(it, Scope(context)) }
        }
        return condition
    }

    override fun visit(
        expr: ReadInputExpr,
        context: Scope,
    ): Any {
        val message = evaluate(expr.value, context) as String
        return inputProvider.input(message)
    }

    override fun visit(
        expr: BooleanExpr,
        context: Scope,
    ): Any {
        return expr.value
    }
}
