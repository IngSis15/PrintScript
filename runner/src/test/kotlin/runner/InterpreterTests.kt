package runner

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import runner.utils.ErrorCollector
import runner.utils.PrintCollector
import runner.utils.TestObserver
import java.io.File
import java.io.FileNotFoundException
import java.util.Optional
import java.util.stream.Stream

class InterpreterTests {
    companion object {
        @JvmStatic
        fun data(): Stream<String> {
            return Stream.of(
                "test-hello",
                "test-declaration",
                "test-assignment",
                "test-operation",
                "test-complex-operation",
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testInterpreter(directory: String) {
        val observer = TestObserver()
        val errorHandler = ErrorCollector()
        val printCollector = PrintCollector()
        val runner =
            Runner(
                listOf(
                    observer,
                ),
            )

        val file = File("src/test/resources/interpreter/$directory/main.ps")
        val expected = readLines("src/test/resources/interpreter/$directory/expected.txt")

        runner.runExecute(file, errorHandler, printCollector)

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
