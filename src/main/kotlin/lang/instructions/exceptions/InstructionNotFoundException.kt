package quark.lang.instructions.exceptions

import quark.lang.exceptions.LanguageException

class InstructionNotFoundException(
    instructionName: String
) : LanguageException("The instruction '$instructionName' does not exist.")