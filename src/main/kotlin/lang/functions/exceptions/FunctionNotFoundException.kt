package quark.lang.functions.exceptions

import quark.lang.exceptions.LanguageException

class FunctionNotFoundException(
    functionName: String
) : LanguageException("The function '$functionName' does not exist.")