package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import org.apache.commons.io.input.ObservableInputStream
import runner.Runner
import java.io.FileInputStream

class Validate : CliktCommand("Validate PrintScript file") {
    private val file by argument().file()
    private val version by option("--version", "-v").default("1.0")

    override fun run() {
        val runner = Runner()
        val fileInputStream = FileInputStream(file)
        val inputStream = ObservableInputStream(fileInputStream)
        inputStream.add(ProgressObserver(file.length()))
        runner.runValidate(inputStream, version, CliErrorHandler())
    }
}
