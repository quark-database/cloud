package quark.lang.types.builtin

import quark.lang.exceptions.MalformedThingCastException
import quark.lang.types.Type

object IntType : Type<Long>(
    Long::class,
    example = 42,
    columnNameExample = "age"
) {
    override fun serialize(nativeObject: Long): String {
        return nativeObject.toString()
    }

    override fun safeCast(nativeObject: Any): Long? {
        return when(nativeObject) {
            is Number -> nativeObject.toLong()
            is String -> nativeObject.toDoubleOrNull()?.toLong() ?: throw MalformedThingCastException("'$nativeObject' is not an integer.")
            else -> null
        }
    }
}