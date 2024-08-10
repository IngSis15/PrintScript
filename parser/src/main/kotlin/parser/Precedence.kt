package parser

enum class Precedence {
    LOWEST,
    SUM,
    MUL,
    DIV,
    CALL,
    HIGHEST,
}