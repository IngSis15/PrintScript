package runner

import formatter.Formatter
import formatter.FormatterConfig
import interpreter.Interpreter
import interpreter.Scope
import lexer.Lexer
import linter.Linter
import parser.Grammar
import parser.Parser
import source.FileReader
import source.PrintEmitter
import java.io.File
import java.nio.file.Path

class Runner(
    private val observers: List<Observer>,
    private val errorHandler: ErrorHandler,
    private val printEmitter: PrintEmitter,
) : Observable {
    fun runExecute(file: File) {
        try {
            val fileSource = FileReader(file)
            val lexer = Lexer()
            val parser = Parser(lexer.lex(fileSource), Grammar())
            val interpreter = Interpreter()
            val scope = Scope()

            notifyObservers(Event(EventType.INFO, "Parsing file: ${file.name}"))

            interpreter.interpret(parser.parse(), scope, printEmitter)
        } catch (e: Exception) {
            errorHandler.handleError(e)
        }
    }

    fun runFormat(
        file: File,
        outputPath: Path,
        config: File,
    ) {
        try {
            val fileSource = FileReader(file)
            val lexer = Lexer()
            val formatter = Formatter(FormatterConfig.fileToConfig(config))

            notifyObservers(Event(EventType.INFO, "Formatting file: ${file.name}"))

            formatter.format(lexer.lex(fileSource))
        } catch (e: Exception) {
            errorHandler.handleError(e)
        }
    }

    fun runAnalyze(file: File) {
        try {
            val lexer = Lexer()
            val linter = Linter()
            val fileSource = FileReader(file)
            val parser = Parser(lexer.lex(fileSource), Grammar())
            val result = linter.lint(parser.parse().asSequence().toList())

            notifyObservers(Event(EventType.INFO, "Analyzing file: ${file.name}"))

            if (result.approved) {
                notifyObservers(Event(EventType.INFO, result.messages[0]))
            } else {
                result.messages.forEach {
                    notifyObservers(Event(EventType.ERROR, it))
                }
            }
        } catch (e: Exception) {
            errorHandler.handleError(e)
        }
    }

    override fun addObserver(observer: Observer): Observable {
        return Runner(observers + observer, errorHandler, printEmitter)
    }

    override fun removeObserver(observer: Observer): Observable {
        return Runner(observers - observer, errorHandler, printEmitter)
    }

    override fun notifyObservers(event: Event) {
        observers.forEach { it.update(event) }
    }
}
