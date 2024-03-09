package quark.lang.lexer.exceptions

import quark.lang.exceptions.LanguageException

sealed class LexerException(override val message: String) : LanguageException(message)
