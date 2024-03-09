package quark.lang.instructions.builtin

import quark.lang.instructions.Instruction
import quark.lang.instructions.InstructionArguments
import quark.lang.instructions.InstructionResultBuilder
import quark.lang.instructions.InstructionResultMessage
import quark.lang.interpreter.Interpreter

class CreateTokenInstruction(interpreter: Interpreter) : Instruction(
    "", 
    interpreter,
    
) {
    override fun onInvoke(arguments: InstructionArguments, result: InstructionResultBuilder): InstructionResultMessage {
        TODO("Not yet implemented")
    }
}