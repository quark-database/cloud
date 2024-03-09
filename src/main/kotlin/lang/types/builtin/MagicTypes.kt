package quark.lang.types.builtin

import quark.lang.types.Type

val Type<*>?.name: String
    get() = this?.name ?: "any"

val AnyType: Type<*>? = null