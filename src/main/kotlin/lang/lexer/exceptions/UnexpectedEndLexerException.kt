package quark.lang.lexer.exceptions

import quark.lang.lexer.LexerState

class UnexpectedEndLexerException(
    state: LexerState
) : LexerException("While reading the $state, the query has ended unexpectedly.")