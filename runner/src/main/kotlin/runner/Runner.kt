package runner

import formatter.Formatter
import formatter.FormatterConfig
import interpreter.Interpreter
import interpreter.Scope
import lexer.Lexer
import lib.PrintEmitter
import lib.TokenWriter
import linter.Linter
import parser.Grammar
import parser.Parser
import java.io.InputStream
import java.io.Writer

class Runner(
    private val observers: List<Observer>,
) : Observable {
    fun runExecute(
        input: InputStream,
        errorHandler: ErrorHandler,
        printEmitter: PrintEmitter,
    ) {
        try {
            val lexer = Lexer(input, "1.0")
            val parser = Parser(lexer.lex(), Grammar())
            val interpreter = Interpreter()
            val scope = Scope()

            notifyObservers(Event(EventType.INFO, "Parsing input."))

            interpreter.interpret(parser.parse(), scope, printEmitter)
        } catch (e: Exception) {
            errorHandler.handleError(Event(EventType.ERROR, e.message ?: "Unknown error"))
        }
    }

    fun runFormat(
        input: InputStream,
        writer: Writer,
        config: InputStream,
        errorHandler: ErrorHandler,
    ) {
        try {
            val lexer = Lexer(input, "1.0")
            val formatter = Formatter(FormatterConfig.streamToConfig(config))

            notifyObservers(Event(EventType.INFO, "Formatting input."))

            val tokenWriter = TokenWriter(formatter.format(lexer.lex()), writer)
            tokenWriter.write()
        } catch (e: Exception) {
            errorHandler.handleError(Event(EventType.ERROR, e.message ?: "Unknown error"))
        }
    }

    fun runAnalyze(
        input: InputStream,
        config: InputStream,
        errorHandler: ErrorHandler,
    ) {
        try {
            val lexer = Lexer(input, "1.0")
            val linter = Linter(config)
            val parser = Parser(lexer.lex(), Grammar())
            val result = linter.lint(parser.parse())

            notifyObservers(Event(EventType.INFO, "Analyzing input."))

            if (result.approved) {
                notifyObservers(Event(EventType.INFO, result.messages[0]))
            } else {
                result.messages.forEach {
                    errorHandler.handleError(Event(EventType.ERROR, it))
                }
            }
        } catch (e: Exception) {
            errorHandler.handleError(Event(EventType.ERROR, e.message ?: "Unknown error"))
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
