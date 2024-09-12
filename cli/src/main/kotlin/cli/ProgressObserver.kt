package cli

import org.apache.commons.io.input.ObservableInputStream

class ProgressObserver(private val totalSize: Long) : ObservableInputStream.Observer() {
    private var bytesRead: Long = 0
    private var lastPercentageReported = -1

    override fun data(value: Int) {
        // Ensure bytesRead does not exceed totalSize
        bytesRead += value
        if (bytesRead > totalSize) {
            bytesRead = totalSize // Cap the bytesRead at totalSize to avoid exceeding 100%
        }

        val progressPercentage = (bytesRead * 100 / totalSize).toInt()

        // Draw progress bar only if the percentage has increased and doesn't exceed 100%
        if (progressPercentage > lastPercentageReported && progressPercentage <= 100) {
            drawProgressBar(progressPercentage)
            lastPercentageReported = progressPercentage
        }
    }

    private fun drawProgressBar(percentage: Int) {
        val totalBars = 50 // Length of the progress bar
        val filledBars = (percentage * totalBars / 100)
        val emptyBars = totalBars - filledBars

        val progressBar = "[" + "#".repeat(filledBars) + " ".repeat(emptyBars) + "]"
        print("\r$progressBar $percentage% completed")
        if (percentage == 100) {
            println() // Move to the next line when done
        }
    }
}
