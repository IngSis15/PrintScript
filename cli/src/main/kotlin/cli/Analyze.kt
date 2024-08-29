package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import lexer.Lexer
import linter.Linter
import parser.Grammar
import parser.Parser
import source.FileReader
import java.io.File

class Analyze : CliktCommand() {
    private val filePath by argument()

    override fun run() {
        try {
            val lexer = Lexer()
            val linter = Linter()
            val fileSource = FileReader(File(filePath))
            val parser = Parser(lexer.lex(fileSource), Grammar())
            val result = linter.lint(parser.parse())

            if (result.approved) {
                println(result.messages)
            } else {
                result.messages.forEach {
                    println(it)
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
