package quark.lang.functions

import quark.lang.functions.exceptions.FunctionInvocationException
import quark.lang.functions.exceptions.FunctionParameterException
import quark.lang.types.builtin.ArrayType

class FunctionParameters(vararg parameters: Pair<String, FunctionParameter<*>>) : Iterable<Pair<String, FunctionParameter<*>>> {
    private val names: List<String>
    private val parameters: List<FunctionParameter<*>>
    internal val positional: List<Pair<String, FunctionParameter<*>>>
        get() {
            val positionalParameters = names zip parameters
            if(hasVarargs) {
                positionalParameters.removeLast()
            }

            return positionalParameters
        }

    init {
        parameters.unzip().let { (names, parameters) ->
            this.names = names
            this.parameters = parameters
        }

        ensureVarargsIsCorrect()
        ensureParameterNamesAreUnique()
    }

    internal val hasVarargs: Boolean
        get() = this.parameters.any { it.varargs }

    internal val varargs: String
        get() = names[this.parameters.indexOfFirst { it.varargs }]

    private fun ensureVarargsIsCorrect() {
        if (parameters.any { parameter -> parameter.varargs && parameter != parameters.last() }) {
            throw FunctionParameterException("The varargs parameter must go the last in the parameter list.")
        }

        if (hasVarargs && get(varargs).type != ArrayType) {
            throw FunctionParameterException("The varargs parameter must have the ArrayType.")
        }
    }

    private fun ensureParameterNamesAreUnique() {
        if (names.distinct() != names) {
            throw FunctionParameterException("The parameters names are not unique.")
        }
    }

    private fun indexOf(name: String): Int {
        return names.indexOf(name)
    }

    internal operator fun contains(name: String): Boolean {
        return name in names
    }

    internal operator fun get(index: Int): Pair<String, FunctionParameter<*>> {
        val parameter = parameters.getOrNull(index)

        if(parameter == null && hasVarargs) {
            return varargs to get(varargs)
        }

        val name = names[index]
        return name to (parameter ?: throw FunctionInvocationException("The parameter index $index is out of range (${parameters.size})."))
    }

    operator fun get(name: String): FunctionParameter<*> {
        return parameters.getOrNull(indexOf(name)) ?: throw FunctionInvocationException("The parameter '$name' is unknown.")
    }

    override fun iterator(): Iterator<Pair<String, FunctionParameter<*>>> {
        return (names zip parameters).iterator()
    }

    override fun toString(): String {
        return (names zip parameters).joinToString { (name, parameter) -> "$name: $parameter" }
    }
}