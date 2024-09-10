package cli

import lib.InputProvider

class CliInputProvider : InputProvider {
    override fun input(): String {
        print("Enter input: ")
        return readLine() ?: ""
    }
}
