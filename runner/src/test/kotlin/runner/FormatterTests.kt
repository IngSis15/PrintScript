package runner

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import runner.utils.ErrorCollector
import java.io.File
import java.io.FileInputStream
import java.io.StringWriter
import java.util.stream.Stream

class FormatterTests {
    companion object {
        @JvmStatic
        fun data(): Stream<Arguments> {
            return Stream.of(
                // Version 1.0
                Arguments.of("simple-test", "1.0"),
                Arguments.of("test-assignation-and-print", "1.0"),
                Arguments.of("test-print", "1.0"),
                // Version 1.1
                Arguments.of("test-if-block", "1.1"),
                Arguments.of("test-nested-if", "1.1"),
                Arguments.of("test-same-line-brace", "1.1"),
                Arguments.of("test-multiple-statements", "1.1"),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testFormatter(
        directory: String,
        version: String,
    ) {
        val errorHandler = ErrorCollector()
        val runner = Runner()

        val file = File("src/test/resources/formatter/$version/$directory/main.ps")
        val expected = File("src/test/resources/formatter/$version/$directory/formatted.ps").readText()
        val config = File("src/test/resources/formatter/$version/$directory/config.json")

        val stringWriter = StringWriter()

        runner.runFormat(FileInputStream(file), version, stringWriter, FileInputStream(config), errorHandler)

        assertEquals(stringWriter.toString(), expected)
    }
}
