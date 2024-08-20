package parser

import org.example.TokenType

class Grammar {
    private val prefixParsers = mutableMapOf<TokenType, PrefixParser>()
    private val infixParsers = mutableMapOf<TokenType, InfixParser>()

    init {
        prefix(TokenType.IDENTIFIER, IdentifierParser())
        prefix(TokenType.NUMBER_LITERAL, LiteralParser())
        prefix(TokenType.STRING_LITERAL, LiteralParser())

        prefix(TokenType.PRINT, PrintCallParser())

        infix(TokenType.ASSIGNATION, AssignParser(0))
        infix(TokenType.SUM, BinaryOperatorParser(1))
        infix(TokenType.SUB, BinaryOperatorParser(1))
        infix(TokenType.MUL, BinaryOperatorParser(2))
        infix(TokenType.DIV, BinaryOperatorParser(2))
    }

    fun getPrefixParser(type: TokenType): PrefixParser? {
        return prefixParsers[type]
    }

    fun getInfixParser(type: TokenType): InfixParser? {
        return infixParsers[type]
    }

    fun prefix(
        type: TokenType,
        parser: PrefixParser,
    ) {
        prefixParsers[type] = parser
    }

    fun infix(
        type: TokenType,
        parser: InfixParser,
    ) {
        infixParsers[type] = parser
    }

    fun getPrecedence(type: TokenType): Int {
        return infixParsers[type]?.getPrecedence() ?: 0
    }
}
