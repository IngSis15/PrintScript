package cli

import lib.PrintEmitter

class CliPrinter : PrintEmitter {
    override fun print(value: String) {
        println(value)
    }
}
