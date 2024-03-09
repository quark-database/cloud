package quark.dbms.tables

import quark.dbms.tables.columns.Column
import quark.lang.interpreter.Interpreter
import quark.lang.types.Thing
import quark.lang.types.builtin.ColumnType
import util.filesystem.Directory

const val TABLE_HEADER_FILENAME = "Header.txt"

internal class TableHeader(directory: Directory, private val interpreter: Interpreter) {
    private val file = directory.getFile(TABLE_HEADER_FILENAME)
    private val columns: List<Column>
        get() = file.lines
            .map(interpreter::evaluate)
            .map(ColumnType::cast)
            .map(Thing<Column>::value)
            .toList()
}

fun createTableHeader(directory: Directory, columns: Array<Column>) {
    val headerFileContent = columns.map(ColumnType::serialize).joinToString("\n")
    val headerFile = directory.createFile(TABLE_HEADER_FILENAME, headerFileContent)
}