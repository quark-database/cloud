package quark.dbms.exceptions

open class DatabaseException(override val message: String) : RuntimeException(message)