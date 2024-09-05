import com.google.gson.Gson
import lexer.Lexer
import linter.Linter
import linter.LinterResult
import linter.linterRules.LintingConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.Grammar
import parser.Parser
import source.StringReader
import java.io.File

class LinterTests {
    fun readLintingRulesConfig(path: String): LintingConfig {
        val file = File(path)
        val gson = Gson()
        val jsonContent = file.readText()
        return gson.fromJson(jsonContent, LintingConfig::class.java)
    }

    fun lintRunner(codeFilePath: String): Boolean {
        val codeLines = TxtFileReader().readTxtFile(codeFilePath)

        val lexer = Lexer()
        val linter = Linter("src/main/resources/lintingRulesConfig.json")

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
        val lintingRules = readLintingRulesConfig("src/main/resources/lintingRulesConfig.json")
        val expected = lintingRules.camelCase
        val path = "src/test/kotlin/camelCase.txt"
        val result = lintRunner(path)
        assertEquals(expected, result)
    }

    @Test
    fun printExpressionWithOperator() {
        val lintingRules = readLintingRulesConfig("src/main/resources/lintingRulesConfig.json")
        val expected = lintingRules.expressionAllowedInPrint
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
        val lintingRules = readLintingRulesConfig("src/main/resources/lintingRulesConfig.json")
        val expected = !lintingRules.camelCase
        val path = "src/test/kotlin/snakeCase.txt"
        val result = lintRunner(path)
        assertEquals(expected, result)
    }
}
