package formatter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

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

    @Test
    fun testStreamToConfigWithTest1() {
        val stream = FileInputStream("src/test/resources/Test1.JSON")
        val expectedConfig =
            FormatterConfig(
                spaceBeforeColon = false,
                spaceAfterColon = true,
                spaceAroundAssignment = true,
                newLinesBeforePrintln = 2,
            )
        val actualConfig = FormatterConfig.streamToConfig(stream)
        assertEquals(expectedConfig, actualConfig)
    }

    @Test
    fun testStreamToConfigWithTest2() {
        val stream = FileInputStream("src/test/resources/Test2.JSON")
        val expectedConfig =
            FormatterConfig(
                spaceBeforeColon = true,
                spaceAfterColon = false,
                spaceAroundAssignment = true,
                newLinesBeforePrintln = 0,
            )
        val actualConfig = FormatterConfig.streamToConfig(stream)
        assertEquals(expectedConfig, actualConfig)
    }

    @Test
    fun testStreamToConfigWithTest3() {
        val stream = FileInputStream("src/test/resources/Test3.JSON")
        val expectedConfig =
            FormatterConfig(
                spaceBeforeColon = true,
                spaceAfterColon = true,
                spaceAroundAssignment = false,
                newLinesBeforePrintln = 1,
                indentSpaces = 6,
            )
        val actualConfig = FormatterConfig.streamToConfig(stream)
        assertEquals(expectedConfig, actualConfig)
    }
}
