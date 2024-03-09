package quark.dbms.tables.properties

import quark.lang.NativeType
import quark.util.classes.friendlyName

abstract class ColumnProperty : NativeType<ColumnProperty> {
    internal val name: String
        get() = friendlyName.replace(" column property", "")

    override fun compareTo(other: ColumnProperty): Int {
        return this.name compareTo other.name
    }
}