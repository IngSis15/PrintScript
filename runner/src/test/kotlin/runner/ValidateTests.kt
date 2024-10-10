package runner

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import runner.utils.ErrorCollector
import java.io.File
import java.io.FileInputStream
import java.util.stream.Stream

class ValidateTests {
    companion object {
        @JvmStatic
        fun data(): Stream<Arguments> {
            return Stream.of(
                // Version 1.0
                Arguments.of("valid-operation", "1.0"),
                Arguments.of("valid-reassignation", "1.0"),
                Arguments.of("invalid-reassignation", "1.0"),
                Arguments.of("invalid-operation", "1.0"),
                Arguments.of("invalid-assignation", "1.0"),
                Arguments.of("valid-assignation", "1.0"),
                // Version 1.1
                Arguments.of("invalid-condition", "1.1"),
                Arguments.of("invalid-const-reassignation", "1.1"),
                Arguments.of("valid-condition", "1.1"),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testValidate(
        filePath: String,
        version: String,
    ) {
        val errorHandler = ErrorCollector()
        val runner = Runner()

        val file = File("src/test/resources/validate/$version/$filePath.ps")

        runner.runValidate(FileInputStream(file), version, errorHandler)

        if (filePath.startsWith("invalid")) {
            assert(errorHandler.getErrors().isNotEmpty())
        } else {
            assert(errorHandler.getErrors().isEmpty())
        }
    }
}
