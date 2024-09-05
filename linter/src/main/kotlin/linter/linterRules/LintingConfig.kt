package linter.linterRules

data class LintingConfig(
    val camelCase : Boolean,
    val expressionAllowedInPrint : Boolean) {
}