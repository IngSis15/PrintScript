package txtReader

import java.io.File

class TxtFileReader {
    fun readTxtFile(filePath: String): List<String> {
        val codeLines = mutableListOf<String>()
        val file = File(filePath)

        if (!file.exists()) {
            println("File not found: $filePath")
            return codeLines
        }

        var currentLine = StringBuilder()

        file.forEachLine { line ->
            currentLine.append(line)

            if (line.contains(";")) {
                codeLines.add(currentLine.toString())
                currentLine.clear()
            }
        }

        if (currentLine.isNotEmpty()) {
            codeLines.add(currentLine.toString())
        }

        return codeLines
    }
}
