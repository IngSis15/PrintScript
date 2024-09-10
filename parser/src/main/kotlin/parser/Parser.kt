package parser

import ast.Expression
import parser.exception.ParseException
import parser.grammar.GrammarV1
import parser.parselets.ExpressionIterator
import token.Token
import token.TokenType

class Parser(private val tokens: Iterator<Token>, private val grammar: GrammarV1) {
    private val tokensRead = mutableListOf<Token>()

    fun parse(): Iterator<Expression> = ExpressionIterator(this)

    fun parseExpression(): Expression {
        return parsePrecedence(0)
    }

    fun parsePrecedence(precedence: Int): Expression {
        val token = consume()
        val parser =
            grammar.getPrefixParser(token.type)
                ?: throw ParseException("No prefix parser for ${token.type}")

        val left = parser.parse(this, token)
        return parseInfix(left, precedence)
    }

    fun parseInfix(
        left: Expression,
        precedence: Int,
    ): Expression {
        var newLeft = left
        while (precedence < grammar.getPrecedence(current().type)) {
            val token = consume()
            val parser =
                grammar.getInfixParser(token.type)
                    ?: throw ParseException("No infix parser for ${current().type}")

            newLeft = parser.parse(this, newLeft, token)
        }

        return newLeft
    }

    fun lookAhead(distance: Int): Token {
        while (distance >= tokensRead.size) {
            tokensRead.add(tokens.next())
        }
        return tokensRead[distance]
    }

    fun match(expected: TokenType): Boolean {
        val token = lookAhead(0)
        if (token.type != expected) {
            return false
        }
        consume()
        return true
    }

    fun matchAny(vararg expected: TokenType): Boolean {
        val token = lookAhead(0)
        if (expected.none { it == token.type }) {
            return false
        }
        consume()
        return true
    }

    fun consume(): Token {
        lookAhead(0)
        return tokensRead.removeAt(0)
    }

    fun consume(expected: TokenType): Token {
        val token = lookAhead(0)
        if (token.type != expected) {
            throw ParseException("Parsing error at ${token.start.line}:${token.start.column}\nExpected $expected, found ${token.literal}")
        }
        return consume()
    }

    fun current(): Token {
        return lookAhead(0)
    }
}
