import cli.Analyze
import cli.Cli
import cli.Execute
import cli.Format
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) =
    Cli()
        .subcommands(Execute(), Format(), Analyze())
        .main(args)
