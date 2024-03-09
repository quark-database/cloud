package quark.lang.functions.exceptions

import quark.lang.exceptions.LanguageException

class FunctionInvocationException(override val message: String) : LanguageException(message)