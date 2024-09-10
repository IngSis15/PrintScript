package parser

import ast.AssignExpr
import ast.CallPrintExpr
import ast.DeclareExpr
import ast.Expression
import ast.IdentifierExpr
import ast.NumberExpr
import ast.OperatorExpr
import ast.StringExpr
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lib.Position
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import parser.exception.ParseException
import parser.factory.ParserFactory
import token.Token
import token.TokenType
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

class ParserTest {
    companion object {
        @JvmStatic
        fun data(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("test-variable", "1.0"),
                Arguments.of("test-sum", "1.0"),
                Arguments.of("test-print", "1.0"),
                Arguments.of("test-declaration", "1.0"),
                Arguments.of("test-precedence", "1.0"),
                Arguments.of("test-complex-op", "1.0"),
                Arguments.of("test-assignation", "1.0"),
            )
        }

        @JvmStatic
        fun invalidData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("test-invalid-declaration", "1.0"),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testParser(
        testFile: String,
        version: String,
    ) {
        val testCaseJson = Files.readString(Paths.get("src/test/resources/parser/$version/$testFile.json"))

        val json =
            Json {
                prettyPrint = true
                classDiscriminator = "type" // This is needed for polymorphic serialization
                ignoreUnknownKeys = true
            }

        val testCase = json.decodeFromString<TestCaseJson>(testCaseJson)

        val tokens = parseTokensFromJson(testCase.tokens)

        val parser = ParserFactory.createParser(version, tokens.iterator())
        val actual =
            parser.parse().asSequence().toList().map {
                parseExpressionToJson(it)
            }

        val expectedJson = json.encodeToString(testCase.expected)
        val actualJson = json.encodeToString(actual)

        assertEquals(expectedJson, actualJson)
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    fun testInvalid(
        testFile: String,
        version: String,
    ) {
        val testCaseJson = Files.readString(Paths.get("src/test/resources/parser/$version/$testFile.json"))

        val json =
            Json {
                prettyPrint = true
                classDiscriminator = "type" // This is needed for polymorphic serialization
                ignoreUnknownKeys = true
            }

        val testTokens = json.decodeFromString<List<TokenJson>>(testCaseJson)

        val tokens = parseTokensFromJson(testTokens)

        val parser = ParserFactory.createParser(version, tokens.iterator())

        assertThrows<ParseException> {
            parser.parse().asSequence().toList()
        }
    }

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
                    parseExpressionToJson(expression.value),
                    PositionJson(expression.pos.line, expression.pos.column),
                )

            is AssignExpr ->
                AssignExprJson(
                    parseExpressionToJson(expression.left),
                    parseExpressionToJson(expression.value),
                    PositionJson(expression.pos.line, expression.pos.column),
                )

            else -> throw IllegalArgumentException("Invalid type")
        }
    }
}
