package quark.lang.functions

import quark.lang.NativeType
import quark.lang.types.Thing
import quark.lang.types.Type

class FunctionParameter<T>(
    val type: Type<T>,
    val required: Boolean = true,
    val varargs: Boolean = false,
    example: T
) where T : NativeType<T> {
    internal val example: Thing<T> = Thing(type, example)

    private val optional: Boolean
        get() = !required

    override fun toString(): String {
        return "$type${if(varargs) "..." else ""}${if(optional) " (optional)" else ""}"
    }
}