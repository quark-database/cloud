package quark.lang.types.builtin

import quark.dbms.tables.columns.Column
import quark.lang.types.Type

object ColumnType : Type<Column>(
    Column::class,
    example = Column("id", IntType),
    columnNameExample = "column type"
) {
    override fun serialize(nativeObject: Column): String {
        TODO("Serialize ColumnType")
    }

    override fun safeCast(nativeObject: Any): Column? {
        return null
    }
}