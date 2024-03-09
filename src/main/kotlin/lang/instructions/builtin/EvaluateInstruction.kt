package quark.lang.instructions.builtin

import quark.lang.instructions.*
import quark.lang.interpreter.Interpreter
import quark.lang.types.builtin.AnyType

class EvaluateInstruction(interpreter: Interpreter) : Instruction(
    "*",
    interpreter,
    "expression" to InstructionParameter(AnyType)
) {
    override fun onInvoke(arguments: InstructionArguments, result: InstructionResultBuilder): InstructionResultMessage {
        val expression = arguments["expression"]

        return "(${expression.type}) $expression"
    }
}