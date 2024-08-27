package org.example.runner

class RunnerImplementation {
    fun run(code: String) {
        val lexer = Lexer()
        val tokens = lexer.lex(code, 0)
        val parser = Parser(tokens.listIterator(), Grammar())
        val program = parser.parse()
        val interpreter = Interpreter()
        interpreter.interpret(program)
    }
}