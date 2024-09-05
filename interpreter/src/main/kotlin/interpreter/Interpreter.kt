package interpreter

import ast.Expression
import source.PrintEmitter

class Interpreter {
    fun interpret(
        program: Iterator<Expression>,
        scope: Scope,
        printEmitter: PrintEmitter,
    ) {
        val evaluator = Evaluator(printEmitter)
        while (program.hasNext()) {
            val expression = program.next()
            evaluator.evaluate(expression, scope)
        }
    }
}
