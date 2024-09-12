package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.path
import runner.Runner
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter

class Format : CliktCommand(help = "Format PrintScript file") {
    private val file by argument().file()
    private val config by option("--config", "-c").file()
    private val outputPath by option("--output", "-o").path(mustExist = false).required()
    private val version by option("-v", "--version").default("1.0")

    override fun run() {
        val runner = Runner()
        val fileWriter = FileWriter(outputPath.toString())

        if (config == null) {
            val configFile = File("src/main/resources/defaultFormatter.json")
            runner.runFormat(FileInputStream(file), version, fileWriter, FileInputStream(configFile), CliErrorHandler())
        } else {
            runner.runFormat(FileInputStream(file), version, fileWriter, FileInputStream(config!!), CliErrorHandler())
        }
    }
}
