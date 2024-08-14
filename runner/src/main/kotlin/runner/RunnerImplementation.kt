package org.example.runner

import parser.Parser
import lexer.Lexer
import interpreter.Interpreter
import parser.Grammar
class RunnerImplementation {

    fun run(code: String) {
        val lexer = Lexer()
        val tokens = lexer.lex(code)
        val parser = Parser(tokens.listIterator(), Grammar())
        val program = parser.parse()
        val interpreter = Interpreter()
        interpreter.interpret(program)
    }


}