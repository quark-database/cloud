package quark.dbms.databases

import quark.dbms.exceptions.TableAlreadyExistsException
import quark.dbms.tables.Table
import quark.dbms.tables.columns.Column
import quark.dbms.tables.createTable
import quark.lang.instructions.Recordable
import quark.lang.instructions.ResultHeader
import quark.lang.instructions.ResultRow
import quark.lang.interpreter.Interpreter
import quark.lang.types.Thing
import quark.lang.types.builtin.StrType
import util.filesystem.Directory

class Database(
    private val directory: Directory,
    private val interpreter: Interpreter
) : Recordable, Iterable<Table> {
    internal val name: String
        get() = directory.name

    private val tables: List<Table>
        get() = directory.directories.map { Table(it, interpreter) }

    private val exists: Boolean
        get() = directory.exists

    override fun iterator(): Iterator<Table> {
        return tables.iterator()
    }

    internal operator fun contains(tableName: String): Boolean {
        return directory.hasDirectory(tableName)
    }

    internal operator fun get(tableName: String): Table {
        return Table(directory.getDirectory(tableName), interpreter)
    }

    internal fun createTable(tableName: String, columns: Array<Column>) {
        if(tableName in this) {
            throw TableAlreadyExistsException(this, tableName)
        }

        createTable(directory, interpreter, tableName, columns)
    }

    internal fun delete() {
        directory.delete()
    }

    override val header: ResultHeader
        get() = ResultHeader("database name")

    override val row: ResultRow
        get() = ResultRow(Thing(StrType, name))
}

