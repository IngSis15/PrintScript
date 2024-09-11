package interpreter

import ast.Expression
import lib.InputProvider
import lib.PrintEmitter

class Interpreter {
    fun interpret(
        program: Iterator<Expression>,
        scope: Scope,
        printEmitter: PrintEmitter,
        inputProvider: InputProvider,
    ) {
        val evaluator = Evaluator(printEmitter, inputProvider)
        while (program.hasNext()) {
            val expression = program.next()
            evaluator.evaluate(expression, scope)
        }
    }
}
