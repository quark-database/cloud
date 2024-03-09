package quark

import quark.dbms.databases.Databases
import quark.lang.functions.FunctionBundle
import quark.lang.functions.builtin.ArrayFunction
import quark.lang.functions.builtin.ReplaceFunction
import quark.lang.instructions.InstructionBundle
import quark.lang.instructions.builtin.*
import quark.lang.interpreter.Interpreter
import quark.lang.types.TypeBundle
import quark.lang.types.builtin.*
import quark.networking.QueryServer
import quark.terminal.promptQueriesInfinitely
import util.filesystem.Directory
import kotlin.io.path.Path

class QuarkCloud {
    private val interpreter = Interpreter()
    private val server = QueryServer { promptQueriesInfinitely(interpreter) }
    private val databases = Databases(Directory(Path("Databases")), interpreter)

    init {
        interpreter.types += TypeBundle(
            IntType,
            NumberType,
            StrType,
            ArrayType,
            ColumnType,
            PropertyType,
            TableNameType,
        )

        interpreter.functions += FunctionBundle(
            ReplaceFunction,
            ArrayFunction,
        )

        interpreter.instructions += InstructionBundle(
            AddPropertyToInstruction(interpreter),
            ChangeInInstruction(interpreter),
            ChangePortToInstruction(interpreter),
            ClearDatabaseInstruction(interpreter),
            ClearTableInstruction(interpreter),
            CloneDatabaseInstruction(interpreter),
            CloneDatabaseSkeletonInstruction(interpreter),
            CloneTableInstruction(interpreter),
            CloneTableSkeletonInstruction(interpreter),
            CountInInstruction(interpreter),
            CreateDatabaseInstruction(interpreter, databases),
            CreateTableInstruction(interpreter, databases),
            CreateTokenInstruction(interpreter),
            DeleteColumnInstruction(interpreter),
            DeleteDatabaseInstruction(interpreter, databases),
            DeleteFromInstruction(interpreter),
            DeleteTableInstruction(interpreter),
            EvaluateInstruction(interpreter),
            GetServerNameInstruction(interpreter),
            GetVersionInstruction(interpreter),
            GrantInstruction(interpreter),
            InsertIntoInstruction(interpreter),
            ListColumnsInstruction(interpreter),
            ListDatabasesInstruction(interpreter, databases),
            ListPropertiesInstruction(interpreter),
            ListTablesInInstruction(interpreter),
            RegrantInstruction(interpreter),
            RenameColumnInInstruction(interpreter),
            RenameDatabaseInstruction(interpreter),
            RenameServerInstruction(interpreter),
            RenameTableInstruction(interpreter),
            ReorderColumnsInstruction(interpreter),
            RevokeFromInstruction(interpreter),
            SecretInstruction(interpreter),
            SelectFromInstruction(interpreter),
            SwapColumnsInstruction(interpreter),
        )

        interpreter.generateColumnFunctions()
    }

    fun launch() {
        server.launch()
    }
}