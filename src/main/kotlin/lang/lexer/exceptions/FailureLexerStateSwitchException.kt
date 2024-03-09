package quark.lang.lexer.exceptions

import quark.lang.lexer.LexerState

class FailureLexerStateSwitchException(
    currentState: LexerState
) : LexerException("The next state for the state '$currentState' was not set.")
