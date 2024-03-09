package quark.lang.instructions

import quark.lang.NativeType
import quark.lang.functions.exceptions.FunctionInvocationException
import quark.lang.types.Thing
import quark.lang.types.Type
import quark.lang.types.builtin.ArrayType

class InstructionArguments(vararg arguments: Pair<String, Thing<*>>) : Iterable<Pair<String, Thing<*>>> {
    private val names: List<String>
    private val arguments: List<Thing<*>>

    init {
        arguments.unzip().let { (names, arguments) ->
            this.names = names
            this.arguments = arguments
        }
    }

    private fun indexOf(name: String): Int {
        return names.indexOf(name)
    }

    internal operator fun contains(name: String): Boolean {
        return name in names
    }

    private operator fun <T> get(index: Int, type: Type<T>): Thing<T> where T : NativeType<T> {
        return type.cast(arguments.getOrNull(index) ?: throw FunctionInvocationException("The parameter index $index is out of range (${arguments.size})."))
    }

    operator fun get(name: String): Thing<*> {
        return arguments.getOrNull(indexOf(name)) ?: throw FunctionInvocationException("The parameter '$name' is unknown.")
    }

    internal inline fun <reified T> getArray(name: String, type: Type<T>): Array<T> where T : NativeType<T> {
        return get(name, ArrayType).toTypedArray(type)
    }

    operator fun <T> get(name: String, type: Type<T>): T where T : NativeType<T> {
        return type.cast(get(name)).value
    }

    override fun iterator(): Iterator<Pair<String, Thing<*>>> {
        return (names zip arguments).iterator()
    }

    override fun toString(): String {
        return joinToString { (name, value) -> "$name = $value" }
    }
}