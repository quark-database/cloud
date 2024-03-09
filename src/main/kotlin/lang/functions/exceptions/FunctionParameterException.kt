package quark.lang.functions.exceptions

import quark.lang.exceptions.LanguageException

class FunctionParameterException(override val message: String) : LanguageException(message)