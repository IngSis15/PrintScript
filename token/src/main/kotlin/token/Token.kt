package token

import lib.Position

data class Token(val type: TokenType, val literal: String, val start: Position)
