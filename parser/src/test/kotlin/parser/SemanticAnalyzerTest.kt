package parser

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import parser.semantic.SemanticAnalyzer
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

class SemanticAnalyzerTest {
    companion object {
        @JvmStatic
        fun data(): Stream<Arguments> {
            return Stream.of(
                // Valid
                Arguments.of("assignation", "valid"),
                Arguments.of("concat-string-number", "valid"),
                Arguments.of("concat-readinput", "valid"),
                // Invalid
                Arguments.of("not-declared", "invalid"),
                Arguments.of("multiply", "invalid"),
                Arguments.of("string-in-condition", "invalid"),
                Arguments.of("boolean-in-readinput", "invalid"),
                Arguments.of("reassign-wrong-type", "invalid"),
                Arguments.of("reassign-const", "invalid"),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testParser(
        testFile: String,
        validity: String,
    ) {
        val testCaseJson = Files.readString(Paths.get("src/test/resources/semantic/$validity/$testFile.json"))

        val json =
            Json {
                prettyPrint = true
                classDiscriminator = "type"
                ignoreUnknownKeys = true
            }

        val testCase = json.decodeFromString<List<ExpectedExprJson>>(testCaseJson)
        val nodes =
            testCase.map {
                parseJsonToExpression(it)
            }
        val semanticAnalyzer = SemanticAnalyzer()

        if (validity == "valid") {
            nodes.forEach {
                val result = semanticAnalyzer.analyze(it)
                assertEquals(true, result.success)
            }
        } else {
            val hasFalse =
                nodes.any {
                    val result = semanticAnalyzer.analyze(it)
                    !result.success
                }
            assertEquals(true, hasFalse)
        }
    }
}
