package quark.lang.functions.exceptions

import quark.lang.hints.FunctionHint

class FunctionArgumentMissingException(functionHint: FunctionHint) : FunctionArgumentException(functionHint.toString())
