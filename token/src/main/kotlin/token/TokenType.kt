package token

enum class TokenType {
    LET_KEYWORD,
    IDENTIFIER,
    CONST_KEYWORD, // added 1.1

    IF, // added 1.1
    ELSE, // added 1.1

    NUMBER_TYPE,
    NUMBER_LITERAL,
    STRING_TYPE,
    STRING_LITERAL,
    BOOLEAN_TYPE, // added 1.1
    BOOLEAN_LITERAL, // added 1.1

    SUM,
    SUB,
    MUL,
    DIV,
    ASSIGNATION,
    PRINT,
    READ_INPUT, // added 1.1
    READ_ENV, // added 1.1

    SEMICOLON,
    COLON,
    LEFT_PAR,
    RIGHT_PAR,
    LEFT_BRACE, // added 1.1
    RIGHT_BRACE, // added 1.1

    ILLEGAL,
    EOF,

    WHITESPACE,
    LINEBREAK,
}
