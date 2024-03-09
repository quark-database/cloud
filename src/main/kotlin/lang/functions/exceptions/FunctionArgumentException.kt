package quark.lang.functions.exceptions

import quark.lang.exceptions.LanguageException

open class FunctionArgumentException(override var message: String) : LanguageException(message)