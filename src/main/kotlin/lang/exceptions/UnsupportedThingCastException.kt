package quark.lang.exceptions

import quark.lang.types.Thing
import quark.lang.types.Type

class UnsupportedThingCastException(
    type: Type<*>,
    thing: Thing<*>
) : LanguageException("The ${thing.type?.name} cast to ${type.name} is unsupported.")