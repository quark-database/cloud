package quark.lang.lexer.exceptions

class UnknownLexerStateException(
    stateName: String
) : LexerException("I don't know how to read a $stateName.")
