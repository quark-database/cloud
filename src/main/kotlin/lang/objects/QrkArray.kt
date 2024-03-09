package quark.lang.objects

import quark.lang.NativeType
import quark.lang.exceptions.LanguageException
import quark.lang.types.Thing
import quark.lang.types.Type

class QrkArray(vararg elements: Thing<*>) : Iterable<Thing<*>>, NativeType<QrkArray> {
    private val elements: MutableList<Thing<*>> = mutableListOf()
    private val size by this.elements::size
    private val elementType: Type<*>?
        get() = elements.firstOrNull()?.type
    internal val empty: Boolean
        get() = size == 0

    init {
        for(element in elements) {
            this += element
        }
    }

    override fun iterator(): Iterator<Thing<*>> {
        return elements.iterator()
    }

    internal operator fun get(index: Int): Thing<*> {
        return elements[index]
    }

    internal operator fun set(index: Int, element: Thing<*>) {
        ensureIncomingElementTypeIsCorrect(element)
        elements[index] = element
    }

    internal operator fun plusAssign(element: Thing<*>) {
        ensureIncomingElementTypeIsCorrect(element)
        elements += element
    }

    internal fun mapCast(type: Type<*>): QrkArray {
        var elements = arrayOf<Thing<*>>()

        for(element in this.elements) {
            elements += type.cast(element)
        }

        return QrkArray(*elements)
    }

    internal inline fun <reified T> toTypedArray(type: Type<T>): Array<T> where T : NativeType<T> {
        var elements = arrayOf<T>()

        for(element in this.elements) {
            elements += type.cast(element).value
        }

        return elements
    }

    private fun ensureIncomingElementTypeIsCorrect(incomingElement: Thing<*>) {
        if (empty) {
            return
        }

        if (!(incomingElement instanceOf elementType)) {
            throw LanguageException("A $elementType array cannot contain (${incomingElement.type}) '$incomingElement'.")
        }
    }

    override fun compareTo(other: QrkArray): Int {
        return this.size compareTo other.size
    }
}

fun <T> createQrkArray(type: Type<T>, vararg elements: T): QrkArray where T : NativeType<T> {
    return QrkArray(*elements.map { Thing(type, it) }.toTypedArray())
}