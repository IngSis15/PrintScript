package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import formatter.Formatter
import formatter.FormatterConfig
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
        val formattedCode = formater.format(inputCode)
        File(filePath).writeText(formattedCode)
    }
}
