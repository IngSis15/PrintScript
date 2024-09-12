package runner

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import runner.utils.ErrorCollector
import java.io.File
import java.io.FileInputStream
import java.util.stream.Stream

class LinterTests {
    companion object {
        @JvmStatic
        fun data(): Stream<String> {
            return Stream.of(
                "valid-camelcase",
                "invalid-camelcase",
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testFormatter(directory: String) {
        val errorHandler = ErrorCollector()
        val runner = Runner()

        val file = File("src/test/resources/linter/$directory/main.ps")
        val config = File("src/test/resources/linter/$directory/config.json")

        runner.runAnalyze(FileInputStream(file), "1.0", FileInputStream(config), errorHandler)

        if (directory.startsWith("invalid")) {
            assert(errorHandler.getErrors().isNotEmpty())
        } else {
            assert(errorHandler.getErrors().isEmpty())
        }
    }
}
