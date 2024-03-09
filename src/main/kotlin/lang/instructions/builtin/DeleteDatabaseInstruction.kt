package quark.lang.instructions.builtin

import quark.dbms.databases.Databases
import quark.lang.instructions.*
import quark.lang.interpreter.Interpreter
import quark.lang.types.builtin.StrType

class DeleteDatabaseInstruction(interpreter: Interpreter, private val databases: Databases) : Instruction(
    "database.delete",
    interpreter,
    "database name" to InstructionParameter(StrType, general = true)
) {
    override fun onInvoke(arguments: InstructionArguments, result: InstructionResultBuilder): InstructionResultMessage {
        val databaseName = arguments["database name", StrType]
        databases[databaseName].delete()

        return "A database '$databaseName' is deleted."
    }
}