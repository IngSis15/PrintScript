package parser

import ast.DeclareExpr
import ast.Expression
import ast.NumberExpr
import ast.TypeExpr
import org.example.Token
import org.example.TokenType
import parser.exception.ParseException

class Parser(private val tokens: Iterator<Token>, val grammar: Grammar) {
    private val tokensRead = mutableListOf<Token>()

    fun parse(): List<Expression> {
        val program: MutableList<Expression> = mutableListOf()

        while (lookAhead(0).type != TokenType.EOF) {
            val expr = parseStatement()
            program.add(expr)
        }

        return program
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

    fun consume(): Token {
        lookAhead(0)
        return tokensRead.removeAt(0)
    }

    fun consume(expected: TokenType): Token {
        val token = lookAhead(0)
        if (token.type != expected) {
            throw Exception("Expected $expected, found $token")
        }
        return consume()
    }

    fun parseStatement(): Expression {
        val expr: Expression = when {
            match(TokenType.LET_KEYWORD) -> parseDeclaration()
            else -> parseExpression()
        }

        if (!match(TokenType.SEMICOLON)) {
            throw ParseException("Expected semicolon at the end of the statement")
        }

        return expr
    }

    fun parseExpression(): Expression {
        return parsePrecedence(0)
    }

    fun parsePrecedence(precedence: Int): Expression {
        val token = consume()
        val parser = grammar.getPrefixParser(token.type)
            ?: throw ParseException("No prefix parser for ${token.type}")

        val left = parser.parse(this, token)
        return parseInfix(left, precedence)
    }

    fun parseInfix(left: Expression, precedence: Int): Expression {
        var newLeft = left
        while (precedence < grammar.getPrecedence(lookAhead(0).type)) {
            val token = consume()
            val parser = grammar.getInfixParser(token.type)
                ?: throw ParseException("No infix parser for ${lookAhead(0).type}")

            newLeft = parser.parse(this, left, token)
        }

        return newLeft
    }

    fun parseDeclaration(): Expression {
        val type = parseTypeDeclaration()

        if (match(TokenType.ASSIGNATION)) {
            val value = parseExpression()
            return DeclareExpr(type, value, 0)
        }

        return DeclareExpr(type, NumberExpr(0, 0), 0)
    }

    fun parseTypeDeclaration(): Expression {
        val name = consume(TokenType.IDENTIFIER).literal
        consume(TokenType.COLON)
        return if (match(TokenType.STRING_TYPE)) {
            TypeExpr(name, "string", 0)
        } else if (match(TokenType.NUMBER_TYPE)) {
            TypeExpr(name, "number", 0)
        } else {
            throw ParseException("Expected type declaration")
        }
    }
}