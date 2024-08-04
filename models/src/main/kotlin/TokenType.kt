package org.example

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

    ILLEGAL,
    EOF,
}
