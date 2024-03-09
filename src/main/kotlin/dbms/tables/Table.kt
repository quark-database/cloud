package quark.dbms.tables

import quark.dbms.databases.Database
import quark.dbms.tables.columns.Column
import quark.lang.instructions.Recordable
import quark.lang.interpreter.Interpreter
import util.filesystem.Directory

class Table(
    private val directory: Directory,
    private val interpreter: Interpreter
): Iterable<Recordable> {
    private val header = TableHeader(directory, interpreter)
    private val database: Database
        get() = Database(directory.parent, interpreter)

    override fun iterator(): Iterator<Recordable> {
        TODO("Not yet implemented")
    }
}

fun createTable(directory: Directory, interpreter: Interpreter, tableName: String, columns: Array<Column>): Table {
    val tableDirectory = directory.createDirectory(tableName)
    createTableHeader(directory, columns)

    return Table(tableDirectory, interpreter)
}
