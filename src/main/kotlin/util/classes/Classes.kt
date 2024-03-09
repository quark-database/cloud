package quark.util.classes

import quark.lang.types.exceptions.NativeTypeCastException
import kotlin.reflect.KClass
import kotlin.reflect.safeCast


val Any?.friendlyName: String
        get() {
            val self = this ?: return "{Empty Object}"
            val className = self::class.simpleName ?: return "{Anonymous Object}"
            val buffer = StringBuilder()
            var wordLength = 0

            for (character in className) {
                if (Character.isUpperCase(character) && wordLength != 1) {
                    buffer.append(' ')
                    wordLength = 0
                }

                buffer.append(character)
                wordLength += 1
            }

            return buffer.toString().trim().lowercase()
        }

inline fun <reified T2> castClassTo(sourceClass: KClass<*>): KClass<T2> where T2 : Any {
    val wantedClass = T2::class

    return wantedClass::class.safeCast(sourceClass) ?: throw NativeTypeCastException(sourceClass, wantedClass)
}
