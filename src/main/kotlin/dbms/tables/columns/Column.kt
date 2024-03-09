package quark.dbms.tables.columns

import quark.dbms.tables.properties.ColumnProperty
import quark.lang.NativeType
import quark.lang.types.Type

class Column(
    private val name: String,
    private val type: Type<*>,
    private vararg val properties: ColumnProperty
) : NativeType<Column> {
    override fun compareTo(other: Column): Int {
        return this.type.name compareTo other.type.name
    }
}