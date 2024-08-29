import lexer.Lexer
import linter.Linter
import linter.LinterResult
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.Grammar
import parser.Parser
import source.StringReader

class LinterTests {
    fun readLintingRulesConfig(): JSONObject {
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("lintingRulesConfig.json")
                ?: throw IllegalArgumentException("File not found!")

        val jsonContent = inputStream.bufferedReader().use { it.readText() }
        return JSONObject(jsonContent)
    }

    fun lintRunner(codeFilePath: String): Boolean {
        val codeLines = TxtFileReader().readTxtFile(codeFilePath)

        val lexer = Lexer()
        val linter = Linter()

        val results = mutableListOf<LinterResult>()

        codeLines.forEachIndexed { _, line ->
            val tokens = lexer.lex(StringReader(line))
            val parser = Parser(tokens, Grammar())
            val expressionList = parser.parse()
            val lintingResult = linter.lint(expressionList)
            if (!lintingResult.approved) {
                results.addLast(lintingResult)
            }
        }
        return results.size == 0
    }

    @Test
    fun camelCase() {
        val lintingRules = readLintingRulesConfig()
        val expected = lintingRules.getBoolean("camelCase")
        val path = "src/test/kotlin/camelCase.txt"
        val result = lintRunner(path)
        assertEquals(expected, result)
    }

    @Test
    fun printExpressionWithOperator() {
        val lintingRules = readLintingRulesConfig()
        val expected = lintingRules.getBoolean("expressionAllowedInPrint")
        val path = "src/test/kotlin/printExpression.txt"
        val result = lintRunner(path)
        assertEquals(expected, result)
    }

    @Test
    fun printExpressionWithoutOperator() {
        val path = "src/test/kotlin/printWithoutExpression.txt"
        val result = lintRunner(path)
        assertTrue(result)
    }

    @Test
    fun snakeCase() {
        val lintingRules = readLintingRulesConfig()
        val expected = !lintingRules.getBoolean("camelCase")
        val path = "src/test/kotlin/snakeCase.txt"
        val result = lintRunner(path)
        assertEquals(expected, result)
    }
}
