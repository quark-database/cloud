package quark.lang.interpreter

import quark.lang.instructions.Instruction
import quark.lang.instructions.InstructionArguments
import quark.lang.instructions.InstructionResult

data class Query(private val instruction: Instruction, internal val arguments: InstructionArguments) {
    internal fun execute(): InstructionResult {
        return instruction(arguments)
    }
}