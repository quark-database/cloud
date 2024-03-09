package quark.lang.types

import quark.lang.NativeType
import quark.lang.exceptions.LanguageException

class Thing<T>(val type: Type<T>?, internal val value: T) where T : NativeType<T> {
    infix fun instanceOf(type: Type<*>?): Boolean {
        return this.type == type
    }

    override fun toString(): String {
        return type?.serialize(value) ?: throw LanguageException("The thing $value cannot be serialized, because its type is Any.")
    }
}