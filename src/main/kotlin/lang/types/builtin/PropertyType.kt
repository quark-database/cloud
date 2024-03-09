package quark.lang.types.builtin

import quark.dbms.tables.properties.ColumnProperty
import quark.dbms.tables.properties.OptionalColumnProperty
import quark.lang.types.Type

object PropertyType : Type<ColumnProperty>(
    ColumnProperty::class,
    example = OptionalColumnProperty(),
    columnNameExample = "column property"
) {
    override fun serialize(nativeObject: ColumnProperty): String {
        TODO()
    }

    override fun safeCast(nativeObject: Any): ColumnProperty? {
        return null
    }
}