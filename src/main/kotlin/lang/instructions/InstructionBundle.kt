package quark.lang.instructions

import quark.lang.instructions.exceptions.InstructionNotFoundException

class InstructionBundle(vararg instructions: Instruction) : Iterable<Instruction> {
    private val instructions = mutableListOf(*instructions)

    internal operator fun plusAssign(instructions: Iterable<Instruction>) {
        this.instructions += instructions
    }

    override fun iterator(): Iterator<Instruction> {
        return instructions.iterator()
    }

    internal operator fun get(instructionName: String): Instruction {
        return instructions.firstOrNull { it.name == instructionName } ?: throw InstructionNotFoundException(instructionName)
    }
}