package formatter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class FormatterConfigTest {
    @Test
    fun testFileToConfigWithTest1() {
        val file = File("src/test/resources/Test1.JSON")
        val expectedConfig =
            FormatterConfig(
                spaceBeforeColon = false,
                spaceAfterColon = true,
                spaceAroundAssignment = true,
                newLinesBeforePrintln = 2,
            )
        val actualConfig = FormatterConfig.fileToConfig(file)
        assertEquals(expectedConfig, actualConfig)
    }

    @Test
    fun testFileToConfigWithTest2() {
        val file = File("src/test/resources/Test2.JSON")
        val expectedConfig =
            FormatterConfig(
                spaceBeforeColon = true,
                spaceAfterColon = false,
                spaceAroundAssignment = true,
                newLinesBeforePrintln = 0,
            )
        val actualConfig = FormatterConfig.fileToConfig(file)
        assertEquals(expectedConfig, actualConfig)
    }
}
