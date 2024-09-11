import lexer.Lexer
import linter.Linter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.Parser
import parser.grammar.Grammar
import parser.grammar.GrammarV1
import parser.grammar.GrammarV11
import java.io.File
import java.io.FileInputStream

class LinterTests {
    private fun lintRunner(
        codeFilePath: String,
        version: String,
    ): Boolean {
        val file = File(codeFilePath)

        val lexer = Lexer(FileInputStream(file), version)
        val linter = Linter(FileInputStream(File("src/main/resources/lintingRulesConfig.json")), version)

        val tokens = lexer.lex()

        var gramm: Grammar = GrammarV1()

        if (version == "1.0") {
            gramm = GrammarV1()
        } else {
            gramm = GrammarV11()
        }
        val parser = Parser(tokens, gramm)
        val expressionList = parser.parse()
        val lintingResult = linter.lint(expressionList)

        return lintingResult.approved
    }

    @Test
    fun camelCase() {
        val path = "src/test/kotlin/camelCase.txt"
        val result = lintRunner(path, "1.0")
        assertEquals(false, result)
    }

    @Test
    fun printExpressionWithOperator() {
        val path = "src/test/kotlin/printExpression.txt"
        val result = lintRunner(path, "1.0")
        assertEquals(false, result)
    }

    @Test
    fun printExpressionWithoutOperator() {
        val path = "src/test/kotlin/printWithoutExpression.txt"
        val result = lintRunner(path, "1.0")
        assertTrue(result)
    }

    @Test
    fun snakeCase() {
        val path = "src/test/kotlin/snakeCase.txt"
        val result = lintRunner(path, "1.0")
        assertEquals(true, result)
    }

    @Test
    fun readInputWithOperator() {
        val path = "src/test/kotlin/readInputWithOperator.txt"
        val result = lintRunner(path, "1.1")
        assertEquals(false, result)
    }
}
