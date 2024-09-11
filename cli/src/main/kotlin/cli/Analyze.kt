package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import runner.Runner
import java.io.File
import java.io.FileInputStream

class Analyze : CliktCommand(help = "Analyze PrintScript file") {
    private val file by argument().file()
    private val config by option("--config", "-c").file()
    private val version by option("--version", "-v").default("1.0")

    override fun run() {
        val runner = Runner(listOf(ProgressPrinter()))

        if (config == null) {
            val configFile = File("src/main/resources/defaultLinter.json")
            runner.runAnalyze(FileInputStream(file), version, FileInputStream(configFile), CliErrorHandler())
        } else {
            runner.runAnalyze(FileInputStream(file), version, FileInputStream(config!!), CliErrorHandler())
        }
    }
}
