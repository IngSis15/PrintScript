package parser.factory

import parser.Parser
import parser.grammar.GrammarV1
import token.Token

class ParserFactory {
    companion object {
        fun createParser(
            version: String,
            tokens: Iterator<Token>,
        ): Parser {
            return when (version) {
                "1.0" -> createParserV1(tokens)
                "1.1" -> createParserV11(tokens)
                else -> throw IllegalArgumentException("Invalid version")
            }
        }

        private fun createParserV1(tokens: Iterator<Token>): Parser {
            return Parser(tokens, GrammarV1())
        }

        private fun createParserV11(tokens: Iterator<Token>): Parser {
            return Parser(tokens, GrammarV1())
        }
    }
}
