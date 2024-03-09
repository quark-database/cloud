package quark.lang.types.builtin

import quark.dbms.tables.TableName
import quark.dbms.tables.toTableName
import quark.lang.types.Type

object TableNameType : Type<TableName>(
    TableName::class,
    example = TableName("My Shop", "Users"),
    columnNameExample = "table name"
) {
    override fun serialize(nativeObject: TableName): String {
        return """"$nativeObject""""
    }

    override fun safeCast(nativeObject: Any): TableName? {
        return when(nativeObject) {
            is String -> nativeObject.toTableName()
            else -> null
        }
    }
}