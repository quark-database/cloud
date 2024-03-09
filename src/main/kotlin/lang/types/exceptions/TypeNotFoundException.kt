package quark.lang.types.exceptions

import quark.lang.exceptions.LanguageException

class TypeNotFoundException(
    typeName: String
) : LanguageException("The type with name $typeName does not exist.")