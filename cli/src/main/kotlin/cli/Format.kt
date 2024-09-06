package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.path
import runner.Runner
import source.FileWriter
import java.io.File

class Format : CliktCommand(help = "Format PrintScript file") {
    private val file by argument().file()
    private val config by option("--config", "-c").file()
    private val outputPath by option("--output", "-o").path(mustExist = false).required()

    override fun run() {
        val runner = Runner(listOf(ProgressPrinter()))
        val fileWriter = FileWriter(outputPath.toString())

        if (config == null) {
            val configFile = File("src/main/resources/defaultFormatter.json")
            runner.runFormat(file, fileWriter, configFile, CliErrorHandler())
        } else {
            runner.runFormat(file, fileWriter, config!!, CliErrorHandler())
        }
    }
}
