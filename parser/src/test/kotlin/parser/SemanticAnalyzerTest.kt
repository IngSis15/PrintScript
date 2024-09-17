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
                Arguments.of("valid-assignation"),
                // Invalid
                Arguments.of("invalid-not-declared"),
                Arguments.of("invalid-multiply"),
                Arguments.of("invalid-string-in-condition"),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testParser(testFile: String) {
        val testCaseJson = Files.readString(Paths.get("src/test/resources/semantic/$testFile.json"))

        val json =
            Json {
                prettyPrint = true
                classDiscriminator = "type" // This is needed for polymorphic serialization
                ignoreUnknownKeys = true
            }

        val testCase = json.decodeFromString<List<ExpectedExprJson>>(testCaseJson)
        val nodes =
            testCase.map {
                parseJsonToExpression(it)
            }
        val semanticAnalyzer = SemanticAnalyzer()

        if (testFile.startsWith("valid")) {
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
