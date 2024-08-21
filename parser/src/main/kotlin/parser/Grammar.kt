package parser

import org.example.TokenType

class Grammar {
    private val prefixParsers = mutableMapOf<TokenType, PrefixParser>()
    private val infixParsers = mutableMapOf<TokenType, InfixParser>()

    init {
        prefix(TokenType.IDENTIFIER, IdentifierParser())
        prefix(TokenType.NUMBER_LITERAL, LiteralParser())
        prefix(TokenType.STRING_LITERAL, LiteralParser())

        prefix(TokenType.LET_KEYWORD, DeclarationParser())
        prefix(TokenType.PRINT, PrintCallParser())

        infix(TokenType.ASSIGNATION, AssignParser(1))
        infix(TokenType.SUM, BinaryOperatorParser(2))
        infix(TokenType.SUB, BinaryOperatorParser(2))
        infix(TokenType.MUL, BinaryOperatorParser(3))
        infix(TokenType.DIV, BinaryOperatorParser(4))
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
