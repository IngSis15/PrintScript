package parser.grammar

import parser.parselets.InfixParser
import parser.parselets.PrefixParser
import token.TokenType

interface Grammar {
    fun getPrefixParser(type: TokenType): PrefixParser?

    fun getInfixParser(type: TokenType): InfixParser?

    fun getPrecedence(type: TokenType): Int
}
