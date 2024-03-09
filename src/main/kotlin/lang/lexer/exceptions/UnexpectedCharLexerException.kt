package quark.lang.lexer.exceptions

import quark.lang.lexer.LexerState

class UnexpectedCharLexerException(
    state: LexerState
) : LexerException("The character '${state.char}' is unknown for $state.")