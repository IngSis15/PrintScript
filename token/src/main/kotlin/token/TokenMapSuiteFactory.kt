package token

class TokenMapSuiteFactory {
    fun createV1(): TokenMapSuite {
        return TokenMapSuite(
            mapOf(
                "let" to TokenType.LET_KEYWORD,
                "println" to TokenType.PRINT,
            ),
            mapOf("number" to TokenType.NUMBER_TYPE, "string" to TokenType.STRING_TYPE),
            mapOf(
                '+' to TokenType.SUM,
                '-' to TokenType.SUB,
                '*' to TokenType.MUL,
                '/' to TokenType.DIV,
                '=' to TokenType.ASSIGNATION,
            ),
            mapOf(
                '=' to TokenType.ASSIGNATION,
                ';' to TokenType.SEMICOLON,
                ':' to TokenType.COLON,
                '(' to TokenType.LEFT_PAR,
                ')' to TokenType.RIGHT_PAR,
            ),
        )
    }

    fun createV2(): TokenMapSuite {
        return TokenMapSuite(
            mapOf(
                "let" to TokenType.LET_KEYWORD,
                "const" to TokenType.CONST_KEYWORD,
                "if" to TokenType.IF,
                "else" to TokenType.ELSE,
                "println" to TokenType.PRINT,
                "readInput" to TokenType.READ_INPUT,
                "readEnv" to TokenType.READ_ENV,
                "false" to TokenType.BOOLEAN_LITERAL,
                "true" to TokenType.BOOLEAN_LITERAL,
            ),
            mapOf(
                "number" to TokenType.NUMBER_TYPE,
                "string" to TokenType.STRING_TYPE,
                "boolean" to TokenType.BOOLEAN_TYPE,
            ),
            mapOf(
                '+' to TokenType.SUM,
                '-' to TokenType.SUB,
                '*' to TokenType.MUL,
                '/' to TokenType.DIV,
                '=' to TokenType.ASSIGNATION,
            ),
            mapOf(
                ';' to TokenType.SEMICOLON,
                ':' to TokenType.COLON,
                '(' to TokenType.LEFT_PAR,
                ')' to TokenType.RIGHT_PAR,
                '{' to TokenType.LEFT_BRACE,
                '}' to TokenType.RIGHT_BRACE,
            ),
        )
    }

    fun createTokenMapSuite(version: String): TokenMapSuite {
        return when (version) {
            "1.0" -> createV1()
            "1.1" -> createV2()
            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
