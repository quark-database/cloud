package quark.lang.instructions.builtin

import quark.dbms.databases.Databases
import quark.lang.instructions.Instruction
import quark.lang.instructions.InstructionArguments
import quark.lang.instructions.InstructionResultBuilder
import quark.lang.instructions.InstructionResultMessage
import quark.lang.interpreter.Interpreter

class ListDatabasesInstruction(interpreter: Interpreter, private val databases: Databases) : Instruction(
    "database.list",
    interpreter
) {
    override fun onInvoke(arguments: InstructionArguments, result: InstructionResultBuilder): InstructionResultMessage {
        result += databases

        return "Here is a list of all databases."
    }
}