package quark.lang.functions

import quark.lang.NativeType
import quark.lang.functions.exceptions.FunctionInvocationException
import quark.lang.functions.exceptions.FunctionParameterException
import quark.lang.types.Thing
import quark.lang.types.Type
import quark.lang.types.builtin.ArrayType

class FunctionArguments(vararg arguments: Pair<String, Thing<*>>) : Iterable<Pair<String, Thing<*>>> {
    private val names: MutableList<String>
    private val arguments: MutableList<Thing<*>>

    init {
        arguments.unzip().let { (names, arguments) ->
            this.names = names.toMutableList()
            this.arguments = arguments.toMutableList()
        }

        ensureArgumentNamesAreUnique()
    }

    private fun ensureArgumentNamesAreUnique() {
        if (names.distinct() != names) {
            throw FunctionParameterException("The argument names are not unique: $this.")
        }
    }

    private fun indexOf(name: String): Int {
        return names.indexOf(name)
    }

    internal operator fun contains(name: String): Boolean {
        return name in names
    }

    internal operator fun get(name: String): Thing<*> {
        return arguments.getOrNull(indexOf(name)) ?: throw FunctionInvocationException("The parameter '$name' is unknown.")
    }

    private operator fun <T> get(index: Int, type: Type<T>): Thing<T> where T : NativeType<T> {
        return type.cast(arguments.getOrNull(index) ?: throw FunctionInvocationException("The parameter index $index is out of range (${arguments.size})."))
    }

    internal operator fun <T> get(name: String, type: Type<T>): T where T : NativeType<T> {
        return type.cast(arguments.getOrNull(indexOf(name)) ?: throw FunctionInvocationException("The parameter '$name' is unknown.")).value
    }

    internal inline fun <reified T> getArray(name: String, type: Type<T>): Array<T> where T : NativeType<T> {
        return get(name, ArrayType).toTypedArray(type)
    }

    internal operator fun set(name: String, thing: Thing<*>) {
        if(contains(name)) {
            arguments[indexOf(name)] = thing
        } else {
            names += name
            arguments += thing
        }
    }

    override fun iterator(): Iterator<Pair<String, Thing<*>>> {
        return (names zip arguments).iterator()
    }

    override fun toString(): String {
        return (names zip arguments).joinToString { (name, argument) -> "$name = $argument" }
    }
}