package token

enum class TokenType {
    LET_KEYWORD,
    IDENTIFIER,

    NUMBER_TYPE,
    NUMBER_LITERAL,
    STRING_TYPE,
    STRING_LITERAL,

    SUM,
    SUB,
    MUL,
    DIV,
    ASSIGNATION,
    PRINT,

    SEMICOLON,
    COLON,
    LEFT_PAR,
    RIGHT_PAR,

    ILLEGAL,
    EOF,

    WHITESPACE,
    LINEBREAK,
}
