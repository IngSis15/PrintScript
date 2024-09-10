package parser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
@SerialName("IdentifierExpr")
data class IdentifierExprJson(
    val name: String,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("TypeExpr")
data class TypeExprJson(
    val name: String,
    val vType: String,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("DeclareExpr")
data class DeclareExprJson(
    val name: String,
    val vtype: String,
    val value: ExpectedExprJson,
    val position: PositionJson,
) : ExpectedExprJson()

@Serializable
@SerialName("AssignExpr")
data class AssignExprJson(
    val left: ExpectedExprJson,
    val value: ExpectedExprJson,
    val position: PositionJson,
) : ExpectedExprJson()
