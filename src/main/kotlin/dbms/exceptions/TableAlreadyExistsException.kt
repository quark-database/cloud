package quark.dbms.exceptions

import quark.dbms.databases.Database

class TableAlreadyExistsException(
    database: Database,
    tableName: String
) : DatabaseException("A table with name $tableName already exists in the ${database.name} database.")