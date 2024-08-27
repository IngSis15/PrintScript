package linter.linterRules

import ast.Expression
import org.example.Position

interface LintingRule {
    fun isValid(expression: Expression): Pair<Boolean, Position>
}
