package quark.lang.exceptions

import quark.lang.types.Thing
import quark.lang.types.Type
import quark.util.classes.friendlyName

class NativeThingCastException(
    type: Type<*>,
    thing: Thing<*>
) : LanguageException("The native cast of ${thing.friendlyName} to ${type.friendlyName} is failed.")