package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import interpreter.Interpreter
import lexer.Lexer
import parser.Grammar
import parser.Parser
import source.FileReader
import java.io.File

class Execute : CliktCommand(help = "Execute PrintScript file") {
    private val filePath by argument()

    override fun run() {
        try {
            val fileSource = FileReader(File(filePath))
            val lexer = Lexer()
            val parser = Parser(lexer.lex(fileSource), Grammar())
            val interpreter = Interpreter()
            interpreter.interpret(parser.parse().asSequence().toList())
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
