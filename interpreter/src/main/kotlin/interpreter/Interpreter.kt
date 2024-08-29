package interpreter

import ast.Expression

class Interpreter {
    val scope = Scope()

    fun interpret(program: Iterator<Expression>) {
        val evaluator = Evaluator()
        while (program.hasNext()) {
            val expression = program.next()
            evaluator.evaluate(expression, scope)
        }
    }
}
