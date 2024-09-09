package linter.linterRules

import ast.Expression
import lib.Position

interface LintingRule {
    fun isValid(expression: Expression): Pair<Boolean, Position>
}
