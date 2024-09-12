package runner

import formatter.Formatter
import formatter.FormatterConfig
import interpreter.Interpreter
import interpreter.Scope
import lexer.Lexer
import lib.InputProvider
import lib.PrintEmitter
import linter.Linter
import parser.factory.ParserFactory
import token.TokenWriter
import java.io.InputStream
import java.io.Writer

class Runner() {
    fun runExecute(
        input: InputStream,
        version: String,
        errorHandler: ErrorHandler,
        printEmitter: PrintEmitter,
        inputProvider: InputProvider,
    ) {
        try {
            val lexer = Lexer(input, version)
            val parser = ParserFactory.createParser(version, lexer.lex())
            val interpreter = Interpreter()
            val scope = Scope(null)

            interpreter.interpret(parser.parse(), scope, printEmitter, inputProvider)
        } catch (e: Exception) {
            errorHandler.handleError(e.message ?: "Unknown error")
        }
    }

    fun runFormat(
        input: InputStream,
        version: String,
        writer: Writer,
        config: InputStream,
        errorHandler: ErrorHandler,
    ) {
        try {
            val lexer = Lexer(input, version)
            val formatter = Formatter(FormatterConfig.streamToConfig(config))

            val tokenWriter = TokenWriter(formatter.format(lexer.lex()), writer)
            tokenWriter.write()
        } catch (e: Exception) {
            errorHandler.handleError(e.message ?: "Unknown error")
        }
    }

    fun runAnalyze(
        input: InputStream,
        version: String,
        config: InputStream,
        errorHandler: ErrorHandler,
    ) {
        try {
            val lexer = Lexer(input, version)
            val linter = Linter(config, version)
            val parser = ParserFactory.createParser(version, lexer.lex())
            val result = linter.lint(parser.parse())

            if (!result.approved) {
                result.messages.forEach {
                    errorHandler.handleError(it)
                }
            }
        } catch (e: Exception) {
            errorHandler.handleError(e.message ?: "Unknown error")
        }
    }
}
