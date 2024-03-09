package quark.lang.functions.exceptions

import quark.lang.exceptions.LanguageException
import quark.lang.hints.FunctionHint

class FunctionArgumentTypeMismatchException(hint: FunctionHint) : LanguageException(hint.toString())