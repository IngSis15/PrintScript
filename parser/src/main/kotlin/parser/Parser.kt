package parser

import ast.Expression
import org.example.Position
import org.example.Token
import org.example.TokenType
import parser.exception.ParseException

class Parser(private val tokens: Iterator<Token>, private val grammar: Grammar) {
    private val tokensRead = mutableListOf<Token>()
    private lateinit var position: Position

    fun parse(): List<Expression> {
        val program: MutableList<Expression> = mutableListOf()

        while (lookAhead(0).type != TokenType.EOF) {
            val expr = parseExpression()
            program.add(expr)
            consume(TokenType.SEMICOLON)
        }

        return program
    }

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
            throw ParseException("Expected $expected, found $token")
        }
        return consume()
    }

    fun current(): Token {
        return lookAhead(0)
    }
}
