package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import formatter.Formatter
import formatter.FormatterConfig
import formatter.TokenStringBuilder
import lexer.Lexer
import source.StringReader
import java.io.File

class Format : CliktCommand() {
    private val filePath by argument()

    override fun run() {
        val config =
            FormatterConfig(
                spaceBeforeColon = true,
                spaceAfterColon = true,
                spaceAroundAssignment = true,
                newLinesBeforePrintln = 1,
            )
        val formater = Formatter(config)
        val inputCode = File(filePath).readText()
        val formattedCode = formater.format(Lexer().lex(StringReader(inputCode)))
        File(filePath).writeText(TokenStringBuilder(formattedCode).buildString())
    }
}
