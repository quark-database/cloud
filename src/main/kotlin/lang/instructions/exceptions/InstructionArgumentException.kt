package quark.lang.instructions.exceptions

import quark.lang.exceptions.LanguageException

class InstructionArgumentException(override val message: String) : LanguageException(message)