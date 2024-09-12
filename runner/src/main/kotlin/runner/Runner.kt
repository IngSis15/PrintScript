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

class Runner(
    private val observers: List<Observer>,
) : Observable {
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

            notifyObservers(Event(EventType.INFO, "Parsing input."))

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

            notifyObservers(Event(EventType.INFO, "Formatting input."))

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

            notifyObservers(Event(EventType.INFO, "Analyzing input."))

            if (result.approved) {
                notifyObservers(Event(EventType.INFO, result.messages[0]))
            } else {
                result.messages.forEach {
                    errorHandler.handleError(it)
                }
            }
        } catch (e: Exception) {
            errorHandler.handleError(e.message ?: "Unknown error")
        }
    }

    override fun addObserver(observer: Observer): Observable {
        return Runner(observers + observer)
    }

    override fun removeObserver(observer: Observer): Observable {
        return Runner(observers - observer)
    }

    override fun notifyObservers(event: Event) {
        observers.forEach { it.update(event) }
    }
}
