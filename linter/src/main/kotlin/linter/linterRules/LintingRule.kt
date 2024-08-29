package linter.linterRules

import ast.Expression
import token.Position

interface LintingRule {
    fun isValid(expression: Expression): Pair<Boolean, Position>
}
