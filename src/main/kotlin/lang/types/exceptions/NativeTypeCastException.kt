package quark.lang.types.exceptions

import quark.lang.exceptions.LanguageException
import kotlin.reflect.KClass

class NativeTypeCastException(
    sourceType: KClass<*>,
    wantedType: KClass<*>
) : LanguageException("Native type cast from ${sourceType.simpleName} to ${wantedType.simpleName} is impossible.")