package cli

import lib.InputProvider

class CliInputProvider : InputProvider {
    override fun input(message: String): String {
        print(message)
        return readLine() ?: ""
    }
}
