package runner

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import runner.utils.ErrorCollector
import runner.utils.PrintCollector
import runner.utils.QueueInputProvider
import runner.utils.Queues.Companion.toQueue
import runner.utils.TestObserver
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Optional
import java.util.stream.Stream

class InterpreterTests {
    companion object {
        @JvmStatic
        fun data(): Stream<Arguments> {
            return Stream.of(
                // Version 1.0
                Arguments.of("test-hello", "1.0"),
                Arguments.of("test-declaration", "1.0"),
                Arguments.of("test-assignment", "1.0"),
                Arguments.of("test-operation", "1.0"),
                Arguments.of("test-complex-operation", "1.0"),
                Arguments.of("test-decimal", "1.0"),
                // Version 1.1
                Arguments.of("test-const", "1.1"),
                Arguments.of("test-readinput", "1.1"),
                Arguments.of("test-many-inputs", "1.1"),
                Arguments.of("test-conditional", "1.1"),
                Arguments.of("test-conditional-variable", "1.1"),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testInterpreter(
        directory: String,
        version: String,
    ) {
        val observer = TestObserver()
        val errorHandler = ErrorCollector()
        val printCollector = PrintCollector()
        val runner =
            Runner(
                listOf(
                    observer,
                ),
            )

        val file = File("src/test/resources/interpreter/$version/$directory/main.ps")
        val expected = readLines("src/test/resources/interpreter/$version/$directory/expected.txt")
        val input = readLinesIfExists("src/test/resources/interpreter/$version/$directory/input.txt")

        runner.runExecute(
            FileInputStream(file),
            version,
            errorHandler,
            printCollector,
            QueueInputProvider(toQueue(input.orElse(emptyList()))),
        )

        val actual = printCollector.getMessages()

        assertEquals(expected, actual)
    }

    @Throws(FileNotFoundException::class)
    private fun readLines(filePath: String): List<String> {
        return readLinesIfExists(filePath).orElseThrow { FileNotFoundException(filePath) }
    }

    private fun readLinesIfExists(filePath: String): Optional<List<String>> {
        val file = File(filePath)
        if (file.exists()) {
            val list = file.readLines()
            return Optional.of(list)
        }
        return Optional.empty()
    }
}
