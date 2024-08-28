package org.example.formatter

data class FormatterConfig(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundAssignment: Boolean,
    val newLinesBeforePrintln: Int,
)
