package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import runner.Runner

class Analyze : CliktCommand(help = "Analyze PrintScript file") {
    private val file by argument().file()

    override fun run() {
        val runner = Runner(listOf(ProgressPrinter()), CliErrorHandler(), CliPrinter())
        runner.runAnalyze(file)
    }
}
