package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import runner.Runner
import java.io.FileInputStream

class Execute : CliktCommand(help = "Execute PrintScript file") {
    private val file by argument().file()
    private val version by option("--version", "-v").default("1.0")

    override fun run() {
        val runner = Runner()
        runner.runExecute(FileInputStream(file), version, CliErrorHandler(), CliPrinter(), CliInputProvider())
    }
}
