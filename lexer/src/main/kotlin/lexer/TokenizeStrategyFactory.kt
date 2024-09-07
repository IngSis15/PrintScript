package lexer

import lexer.tokenizeStrategies.KeywordStrategy
import lexer.tokenizeStrategies.NumberLiteralStrategy
import lexer.tokenizeStrategies.OperatorStrategy
import lexer.tokenizeStrategies.StringLiteralStrategy
import lexer.tokenizeStrategies.SymbolStrategy
import token.TokenType

class TokenizeStrategyFactory(
    private val keywords: Map<String, TokenType>,
    private val types: Map<String, TokenType>,
    private val operators: Map<Char, TokenType>,
    private val symbols: Map<Char, TokenType>,
) {
    fun getV1(ch: Char): TokenizeStrategy {
        return when {
            ch in 'a'..'z' || ch in 'A'..'Z' || ch == '_' -> KeywordStrategy(keywords, types)
            ch in '0'..'9' -> NumberLiteralStrategy()
            ch == '"' || ch == '\'' -> StringLiteralStrategy(ch)
            ch in "+-*/=" -> OperatorStrategy(operators, ch)
            ch in ";:()" -> SymbolStrategy(symbols, ch)
            else -> SymbolStrategy(symbols, ch)
        }
    }

    fun getV2(ch: Char): TokenizeStrategy {
        return when {
            ch in 'a'..'z' || ch in 'A'..'Z' || ch == '_' -> KeywordStrategy(keywords, types)
            ch in '0'..'9' -> NumberLiteralStrategy()
            ch == '"' || ch == '\'' -> StringLiteralStrategy(ch)
            ch in "+-*/=" -> OperatorStrategy(operators, ch)
            ch in ";:(){}" -> SymbolStrategy(symbols, ch)
            else -> SymbolStrategy(symbols, ch)
        }
    }

    fun getStrategy(
        version: String,
        ch: Char,
    ): TokenizeStrategy {
        return when (version) {
            "1.0" -> getV1(ch)
            "1.1" -> getV2(ch)
            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
