package quark.lang.types

import quark.lang.NativeType
import quark.lang.types.exceptions.TypeNotFoundException

class TypeBundle(vararg types: Type<*>) : Iterable<Type<*>> {
    private val types = mutableListOf(*types)

    internal operator fun plusAssign(types: Iterable<Type<*>>) {
        this.types += types
    }

    override fun iterator(): Iterator<Type<*>> {
        return types.iterator()
    }

    internal operator fun get(typeName: String): Type<*> {
        return types.firstOrNull { it.name == typeName } ?: throw TypeNotFoundException(typeName)
    }

    internal inline fun <reified T> typeOf(nativeObject: T): Type<T> where T : NativeType<T> {
        val type = types.firstOrNull { it.nativeClass.isInstance(nativeObject) } ?: throw TypeNotFoundException("(native) ${nativeObject::class.simpleName}")
        return type.castNativelyTo<T>()
    }
}