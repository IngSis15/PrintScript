package parser

import ast.AssignExpr
import ast.BooleanExpr
import ast.CallPrintExpr
import ast.ConditionalExpr
import ast.DeclareExpr
import ast.Expression
import ast.IdentifierExpr
import ast.NumberExpr
import ast.OperatorExpr
import ast.ReadEnvExpr
import ast.ReadInputExpr
import ast.StringExpr
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lib.Position
import token.Token
import token.TokenType

@Serializable
data class TestCaseJson(
    val tokens: List<TokenJson>,
    val expected: List<ExpectedExprJson>,
)

@Serializable
data class TokenJson(
    val type: String,
    val literal: String,
    val position: PositionJson,
)

@Serializable
data class PositionJson(
    val line: Int,
    val column: Int,
)

@Serializable
sealed class ExpectedExprJson

@Serializable
@SerialName("CallPrintExpr")
data class CallPrintExprJson(
    val arg: ExpectedExprJson,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("OperatorExpr")
data class OperatorExprJson(
    val left: ExpectedExprJson,
    val operator: String,
    val right: ExpectedExprJson,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("NumberExpr")
data class NumberExprJson(
    val value: String,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("StringExpr")
data class StringExprJson(
    val value: String,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("BooleanExpr")
data class BooleanExprJson(
    val value: Boolean,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("IdentifierExpr")
data class IdentifierExprJson(
    val name: String,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("DeclareExpr")
data class DeclareExprJson(
    val name: String,
    val vtype: String,
    val value: ExpectedExprJson?,
    val mutable: Boolean,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("AssignExpr")
data class AssignExprJson(
    val left: ExpectedExprJson,
    val value: ExpectedExprJson,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("ConditionalExpr")
data class ConditionalExprJson(
    val condition: ExpectedExprJson,
    val body: List<ExpectedExprJson>,
    val elseBody: List<ExpectedExprJson>,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("ReadEnvExpr")
data class ReadEnvExprJson(
    val name: ExpectedExprJson,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("ReadInputExpr")
data class ReadInputExprJson(
    val value: ExpectedExprJson,
    val position: PositionJson,
) : ExpectedExprJson()

fun parseTokensFromJson(jsonTokens: List<TokenJson>): List<Token> {
    return jsonTokens.map {
        Token(TokenType.valueOf(it.type), it.literal, Position(it.position.line, it.position.column))
    }
}

fun parseExpressionToJson(expression: Expression): ExpectedExprJson {
    return when (expression) {
        is IdentifierExpr ->
            IdentifierExprJson(
                expression.name,
                PositionJson(expression.pos.line, expression.pos.column),
            )

        is NumberExpr ->
            NumberExprJson(
                expression.value.toString(),
                PositionJson(expression.pos.line, expression.pos.column),
            )

        is StringExpr ->
            StringExprJson(
                expression.value,
                PositionJson(expression.pos.line, expression.pos.column),
            )

        is BooleanExpr ->
            BooleanExprJson(
                expression.value,
                PositionJson(expression.pos.line, expression.pos.column),
            )

        is OperatorExpr ->
            OperatorExprJson(
                parseExpressionToJson(expression.left),
                expression.op,
                parseExpressionToJson(expression.right),
                PositionJson(expression.pos.line, expression.pos.column),
            )

        is CallPrintExpr ->
            CallPrintExprJson(
                parseExpressionToJson(expression.arg),
                PositionJson(expression.pos.line, expression.pos.column),
            )

        is DeclareExpr ->
            DeclareExprJson(
                expression.name,
                expression.type,
                expression.value?.let { parseExpressionToJson(it) },
                expression.mutable,
                PositionJson(expression.pos.line, expression.pos.column),
            )

        is AssignExpr ->
            AssignExprJson(
                parseExpressionToJson(expression.left),
                parseExpressionToJson(expression.value),
                PositionJson(expression.pos.line, expression.pos.column),
            )

        is ConditionalExpr ->
            ConditionalExprJson(
                parseExpressionToJson(expression.condition),
                expression.body.map { parseExpressionToJson(it) },
                expression.elseBody.map { parseExpressionToJson(it) },
                PositionJson(expression.position.line, expression.position.column),
            )

        is ReadEnvExpr ->
            ReadEnvExprJson(
                parseExpressionToJson(expression.name),
                PositionJson(expression.pos.line, expression.pos.column),
            )

        is ReadInputExpr ->
            ReadInputExprJson(
                parseExpressionToJson(expression.value),
                PositionJson(expression.pos.line, expression.pos.column),
            )

        else -> throw IllegalArgumentException("Invalid type")
    }
}

fun parseJsonToExpression(json: ExpectedExprJson): Expression {
    return when (json) {
        is IdentifierExprJson ->
            IdentifierExpr(json.name, Position(json.position.line, json.position.column))

        is NumberExprJson ->
            NumberExpr(json.value.toDouble(), Position(json.position.line, json.position.column))

        is StringExprJson ->
            StringExpr(json.value, Position(json.position.line, json.position.column))

        is BooleanExprJson ->
            BooleanExpr(json.value, Position(json.position.line, json.position.column))

        is OperatorExprJson ->
            OperatorExpr(
                parseJsonToExpression(json.left),
                json.operator,
                parseJsonToExpression(json.right),
                Position(json.position.line, json.position.column),
            )

        is CallPrintExprJson ->
            CallPrintExpr(
                parseJsonToExpression(json.arg),
                Position(json.position.line, json.position.column),
            )

        is DeclareExprJson ->
            DeclareExpr(
                json.name,
                json.vtype,
                json.value?.let { parseJsonToExpression(it) },
                json.mutable,
                Position(json.position.line, json.position.column),
            )

        is AssignExprJson ->
            AssignExpr(
                parseJsonToExpression(json.left),
                parseJsonToExpression(json.value),
                Position(json.position.line, json.position.column),
            )

        is ConditionalExprJson ->
            ConditionalExpr(
                parseJsonToExpression(json.condition),
                json.body.map { parseJsonToExpression(it) },
                json.elseBody.map { parseJsonToExpression(it) },
                Position(json.position.line, json.position.column),
            )

        is ReadEnvExprJson ->
            ReadEnvExpr(
                parseJsonToExpression(json.name),
                Position(json.position.line, json.position.column),
            )

        is ReadInputExprJson ->
            ReadInputExpr(
                parseJsonToExpression(json.value),
                Position(json.position.line, json.position.column),
            )

        else -> throw IllegalArgumentException("Invalid type")
    }
}
