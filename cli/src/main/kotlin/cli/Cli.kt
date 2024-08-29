package cli

import com.github.ajalt.clikt.core.CliktCommand

class Cli : CliktCommand(name = "ps") {
    override fun run() = Unit
}
