import lexer.Lexer
import linter.Linter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.Grammar
import parser.Parser
import java.io.File
import java.io.FileInputStream

class LinterTests {
    private fun lintRunner(codeFilePath: String): Boolean {
        val file = File(codeFilePath)

        val lexer = Lexer(FileInputStream(file), "1.0")
        val linter = Linter(FileInputStream(File("src/main/resources/lintingRulesConfig.json")))

        val tokens = lexer.lex()
        val parser = Parser(tokens, Grammar())
        val expressionList = parser.parse()
        val lintingResult = linter.lint(expressionList)

        return lintingResult.approved
    }

    @Test
    fun camelCase() {
        val path = "src/test/kotlin/camelCase.txt"
        val result = lintRunner(path)
        assertEquals(false, result)
    }

    @Test
    fun printExpressionWithOperator() {
        val path = "src/test/kotlin/printExpression.txt"
        val result = lintRunner(path)
        assertEquals(false, result)
    }

    @Test
    fun printExpressionWithoutOperator() {
        val path = "src/test/kotlin/printWithoutExpression.txt"
        val result = lintRunner(path)
        assertTrue(result)
    }

    @Test
    fun snakeCase() {
        val path = "src/test/kotlin/snakeCase.txt"
        val result = lintRunner(path)
        assertEquals(true, result)
    }
}
