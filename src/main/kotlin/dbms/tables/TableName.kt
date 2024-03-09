package quark.dbms.tables

import quark.lang.NativeType
import quark.lang.exceptions.LanguageException

data class TableName(
    val databaseName: String,
    val tableName: String,
) : NativeType<TableName> {
    override fun compareTo(other: TableName): Int {
        return this.toString() compareTo other.toString()
    }

    override fun toString(): String {
        return "$databaseName.$tableName"
    }
}

fun String.toTableName(): TableName {
    if(!contains('.')) {
        throw LanguageException("A table name '$this' must contains a dot.")
    }

    val (databaseName, tableName) = split('.')
    return TableName(databaseName, tableName)
}