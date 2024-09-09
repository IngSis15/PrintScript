package token

class TokenMapSuite(
    val keywords: Map<String, TokenType>,
    val types: Map<String, TokenType>,
    val operators: Map<Char, TokenType>,
    val symbols: Map<Char, TokenType>,
)
