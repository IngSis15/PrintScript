package org.example.formatter

import java.io.File

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


    fun formatFile(inputFilePath: String, outputFilePath: String) {
        val inputCode = File(inputFilePath).readText()
        val formattedCode = format(inputCode)
        File(outputFilePath).writeText(formattedCode)
    }

    fun formatSpaceBeforeColon(input: String): String {
        return if (config.spaceBeforeColon) {
            input.replace(Regex("([^\\s]):"), "$1 :")
        } else {
            input.replace(Regex("([^\\s])\\s+:"), "$1:")
        }
    }

    fun formatSpaceAfterColon(input: String): String {
        return if (config.spaceAfterColon) {
            input.replace(Regex(":([^\\s])"), ": $1")
        } else {
            input.replace(Regex(":\\s+([^\\s])"), ":$1")
        }
    }

    fun formatSpaceAroundAssignment(input: String): String {
        return if (config.spaceAroundAssignment) {
            input.replace(Regex("([^\\s])=([^\\s])"), "$1 = $2")
        } else {
            input.replace(Regex("([^\\s])\\s+=\\s+([^\\s])"), "$1=$2")
        }
    }

    fun formatNewLinesBeforePrintln(input: String): String {
        return input.replace(Regex("\\s*println"), "\n".repeat(config.newLinesBeforePrintln) + "println")
    }

    fun formatNewLineAfterSemicolon(input: String): String {
        // Ensure no extra space before the newline after semicolon
        return input.replace(Regex(";\\s*"), ";\n")
    }



    fun formatSingleSpaceBetweenTokens(input: String): String {
        return input.replace(Regex("\\s+"), " ")
    }

    fun formatSpaceAroundOperators(input: String): String {
        return input.replace(Regex("([^\\s])([+\\-*/])([^\\s])"), "$1 $2 $3")
    }
}
