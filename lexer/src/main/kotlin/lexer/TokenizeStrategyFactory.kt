package lexer

import lexer.tokenizeStrategies.KeywordStrategy
import lexer.tokenizeStrategies.NumberLiteralStrategy
import lexer.tokenizeStrategies.OperatorStrategy
import lexer.tokenizeStrategies.StringLiteralStrategy
import lexer.tokenizeStrategies.SymbolStrategy
import token.TokenMapSuite

class TokenizeStrategyFactory(
    private val tokenMapSuite: TokenMapSuite,
) {
    fun getV1(ch: Char): TokenizeStrategy {
        return when {
            ch in 'a'..'z' || ch in 'A'..'Z' || ch == '_' -> KeywordStrategy(tokenMapSuite.keywords, tokenMapSuite.types)
            ch in '0'..'9' -> NumberLiteralStrategy()
            ch == '"' || ch == '\'' -> StringLiteralStrategy(ch)
            ch in "+-*/=" -> OperatorStrategy(tokenMapSuite.operators, ch)
            ch in ";:()" -> SymbolStrategy(tokenMapSuite.symbols, ch)
            else -> SymbolStrategy(tokenMapSuite.symbols, ch)
        }
    }

    fun getV2(ch: Char): TokenizeStrategy {
        return when {
            ch in 'a'..'z' || ch in 'A'..'Z' || ch == '_' -> KeywordStrategy(tokenMapSuite.keywords, tokenMapSuite.types)
            ch in '0'..'9' -> NumberLiteralStrategy()
            ch == '"' || ch == '\'' -> StringLiteralStrategy(ch)
            ch in "+-*/=" -> OperatorStrategy(tokenMapSuite.operators, ch)
            ch in ";:(){}" -> SymbolStrategy(tokenMapSuite.symbols, ch)
            else -> SymbolStrategy(tokenMapSuite.symbols, ch)
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
