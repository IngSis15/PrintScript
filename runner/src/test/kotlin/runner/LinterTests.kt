package runner

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import runner.utils.ErrorCollector
import java.io.File
import java.io.FileInputStream
import java.util.stream.Stream

class LinterTests {
    companion object {
        @JvmStatic
        fun data(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("invalid-camelcase", "1.0"),
                Arguments.of("invalid-print-number-expression-with-literal", "1.0"),
                Arguments.of("invalid-print-number-expression-with-variables", "1.0"),
                Arguments.of("invalid-print-string-expression-with-literal", "1.0"),
                Arguments.of("invalid-print-string-expression-with-variables", "1.0"),
                Arguments.of("invalid-snakecase", "1.0"),
                Arguments.of("valid-camelcase", "1.0"),
                Arguments.of("valid-print-number-expression-with-literal", "1.0"),
                Arguments.of("valid-print-number-expression-with-variables", "1.0"),
                Arguments.of("valid-print-string-expression-with-literal", "1.0"),
                Arguments.of("valid-print-string-expression-with-variables", "1.0"),
                Arguments.of("valid-print-variable-while-no-expression", "1.0"),
                Arguments.of("valid-snakecase", "1.0"),
                Arguments.of("invalid-nested-readInput-with-expression", "1.1"),
                Arguments.of("invalid-readInput-with-expression", "1.1"),
                Arguments.of("valid-nested-readInput-with-expression", "1.1"),
                Arguments.of("valid-readInput-with-expression", "1.1"),
                Arguments.of("valid-readInput-without-expression", "1.1"),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testLinter(
        directory: String,
        version: String,
    ) {
        val errorHandler = ErrorCollector()
        val runner = Runner()

        val file = File("src/test/resources/linter/$version/$directory/main.ps")
        val config = File("src/test/resources/linter/$version/$directory/config.json")

        runner.runAnalyze(FileInputStream(file), version, FileInputStream(config), errorHandler)

        if (directory.startsWith("invalid")) {
            assert(errorHandler.getErrors().isNotEmpty())
        } else {
            assert(errorHandler.getErrors().isEmpty())
        }
    }
}
