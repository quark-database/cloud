package quark.dbms.databases

import quark.lang.interpreter.Interpreter
import util.filesystem.Directory

class Databases(private val directory: Directory, private val interpreter: Interpreter): Iterable<Database> {
    internal operator fun get(databaseName: String): Database {
        return Database(directory.getDirectory(databaseName), interpreter)
    }

    fun create(databaseName: String): Database {
        directory.createDirectory(databaseName)
        return get(databaseName)
    }

    override fun iterator(): Iterator<Database> {
        return directory.directories.map { Database(it, interpreter) }.iterator()
    }
}