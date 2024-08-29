package formatter

class Formatter(private val config: FormatterConfig) {
    fun format(input: String): String {
        var formattedCode = input

        // Apply formatting rules in the correct order
        formattedCode = formatSingleSpaceBetweenTokens(formattedCode)
        formattedCode = formatSpaceBeforeColon(formattedCode)
        formattedCode = formatSpaceAfterColon(formattedCode)
        formattedCode = formatSpaceAroundAssignment(formattedCode)
        formattedCode = formatSpaceAroundOperators(formattedCode)
        formattedCode = formatNewLinesBeforePrintln(formattedCode)
        formattedCode = formatNewLineAfterSemicolon(formattedCode)

        return formattedCode
    }

    fun formatSingleSpaceBetweenTokens(input: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            if (input[i].isWhitespace() && (i == 0 || input[i - 1].isWhitespace())) {
                i++
                continue
            }
            sb.append(input[i])
            i++
        }
        return sb.toString()
    }

    fun formatSpaceBeforeColon(input: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            if (input[i] == ':' && i > 0 && !input[i - 1].isWhitespace() && config.spaceBeforeColon) {
                sb.append(' ')
            }
            sb.append(input[i])
            i++
        }
        return sb.toString()
    }

    fun formatSpaceAfterColon(input: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            sb.append(input[i])
            if (input[i] == ':' && i < input.length - 1 && !input[i + 1].isWhitespace() && config.spaceAfterColon) {
                sb.append(' ')
            }
            i++
        }
        return sb.toString()
    }

    fun formatSpaceAroundAssignment(input: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            if (input[i] == '=' && (i == 0 || input[i - 1] != ' ')) {
                sb.append(' ')
            }
            sb.append(input[i])
            if (input[i] == '=' && (i == input.length - 1 || input[i + 1] != ' ')) {
                sb.append(' ')
            }
            i++
        }
        return sb.toString()
    }

    fun formatSpaceAroundOperators(input: String): String {
        val operators = setOf('+', '-', '*', '/')
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            if (input[i] in operators && (i == 0 || input[i - 1] != ' ')) {
                sb.append(' ')
            }
            sb.append(input[i])
            if (input[i] in operators && (i == input.length - 1 || input[i + 1] != ' ')) {
                sb.append(' ')
            }
            i++
        }
        return sb.toString()
    }

    fun formatNewLinesBeforePrintln(input: String): String {
        val sb = StringBuilder()
        var i = 0
        val numNewLines = config.newLinesBeforePrintln

        while (i < input.length) {
            if (input.startsWith("println(", i)) {
                // Count the number of new lines before `println`
                var newLineCount = 0
                var j = i - 1
                while (j >= 0 && input[j] == '\n') {
                    newLineCount++
                    j--
                }
                // Add the required number of new lines before `println`
                if (newLineCount > numNewLines) {
                    // Remove excess new lines
                    sb.setLength(sb.length - (newLineCount - numNewLines))
                } else if (newLineCount < numNewLines) {
                    // Add missing new lines
                    repeat(numNewLines - newLineCount) {
                        sb.append('\n')
                    }
                }
                // Append `println` and move the index forward
                sb.append("println(")
                i += "println(".length
            } else {
                sb.append(input[i])
                i++
            }
        }
        return sb.toString()
    }

    fun formatNewLineAfterSemicolon(input: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            sb.append(input[i])
            if (input[i] == ';') {
                sb.append('\n')
                if (i < input.length - 1 && input[i + 1] == ' ') {
                    i++ // Skip the space after the semicolon
                }
            }
            i++
        }
        return sb.toString()
    }
}
