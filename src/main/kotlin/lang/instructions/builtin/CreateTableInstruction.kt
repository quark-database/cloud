package quark.lang.instructions.builtin

import quark.dbms.databases.Databases
import quark.lang.instructions.*
import quark.lang.interpreter.Interpreter
import quark.lang.types.builtin.ArrayType
import quark.lang.types.builtin.ColumnType
import quark.lang.types.builtin.StrType
import quark.lang.types.builtin.TableNameType

class CreateTableInstruction(interpreter: Interpreter, private val databases: Databases) : Instruction(
    "table.create",
    interpreter,

    "table name" to InstructionParameter(StrType, general = true),
    "columns" to InstructionParameter(ArrayType)
) {
    override fun onInvoke(arguments: InstructionArguments, result: InstructionResultBuilder): InstructionResultMessage {
        val (databaseName, tableName) = arguments["table name", TableNameType]
        val columns = arguments.getArray("columns", ColumnType)

        databases[databaseName].createTable(tableName, columns)

        return "A table '$tableName' is created"
    }
}