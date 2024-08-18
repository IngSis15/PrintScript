package interpreter

import ast.Expression

class Interpreter {
    private val scope = Scope()

    fun interpret(program: List<Expression>) {
        val evaluator = Evaluator()
        program.forEach {
            evaluator.evaluate(it, scope)
        }
    }
}
