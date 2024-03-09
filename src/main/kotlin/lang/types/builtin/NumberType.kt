package quark.lang.types.builtin

import quark.lang.exceptions.MalformedThingCastException
import quark.lang.types.Type

object NumberType : Type<Double>(
    Double::class,
    example = 3.14,
    columnNameExample = "film rating"
) {
    override fun serialize(nativeObject: Double): String {
        return nativeObject.toString()
    }

    override fun safeCast(nativeObject: Any): Double? {
        return when(nativeObject) {
            is Long -> nativeObject.toDouble()
            is String -> nativeObject.toDoubleOrNull() ?: throw MalformedThingCastException("'$nativeObject' is not a number.")
            else -> null
        }
    }
}