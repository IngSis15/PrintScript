package cli

import source.PrintEmitter

class CliPrinter : PrintEmitter {
    override fun print(value: String) {
        println(value)
    }
}
