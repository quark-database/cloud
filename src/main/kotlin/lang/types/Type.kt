package quark.lang.types

import quark.lang.NativeType
import quark.lang.exceptions.LanguageException
import quark.lang.exceptions.NativeThingCastException
import quark.lang.exceptions.UnsupportedThingCastException
import quark.lang.types.builtin.AnyType
import quark.util.classes.castClassTo
import quark.util.classes.friendlyName
import kotlin.reflect.KClass
import kotlin.reflect.cast
import kotlin.reflect.safeCast

abstract class Type<T>(
    internal val nativeClass: KClass<T>,
    internal val example: T,
    internal val columnNameExample: String,
) where T : NativeType<T> {
    internal val name by lazy { friendlyName.replace(" type", "") }

    fun cast(thing: Thing<*>): Thing<T> {
        if(thing instanceOf this) {
            return Thing(this, nativeClass.safeCast(thing.value) ?: throw NativeThingCastException(this, thing))
        }

        if(thing instanceOf AnyType) {
            throw LanguageException("The thing of Any type cannot be casted.")
        }

        return Thing(this, safeCast(thing.value) ?: throw UnsupportedThingCastException(this, thing))
    }

    internal inline fun <reified U> castNativelyTo(): Type<U> where U : NativeType<U> {
        val safeCaster = this::safeCast
        val serializer = this::serialize
        val sourceType: KClass<T> = nativeClass
        val wantedType: KClass<U> = castClassTo<U>(sourceType)

        return object : Type<U>(wantedType, wantedType.cast(example), columnNameExample) {
            override fun serialize(nativeObject: U): String {
                return serializer(sourceType.cast(nativeObject))
            }

            override fun safeCast(nativeObject: Any): U {
                return wantedType.cast(safeCaster(nativeObject))
            }
        }
    }

    abstract fun serialize(nativeObject: T): String
    abstract fun safeCast(nativeObject: Any): T?

    override fun toString(): String {
        return friendlyName.replace(" type", "")
    }
}