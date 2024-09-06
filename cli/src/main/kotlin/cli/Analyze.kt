package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import runner.Runner
import java.io.File

class Analyze : CliktCommand(help = "Analyze PrintScript file") {
    private val file by argument().file()
    private val config by option("--config", "-c").file()

    override fun run() {
        val runner = Runner(listOf(ProgressPrinter()))

        if (config == null) {
            val configFile = File("src/main/resources/defaultLinter.json")
            runner.runAnalyze(file, configFile.path, CliErrorHandler())
        } else {
            runner.runAnalyze(file, config!!.path, CliErrorHandler())
        }
    }
}
