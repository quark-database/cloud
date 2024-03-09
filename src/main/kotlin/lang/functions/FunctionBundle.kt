package quark.lang.functions

import quark.lang.functions.exceptions.FunctionNotFoundException

class FunctionBundle(vararg functions: Function<*>) : Iterable<Function<*>> {
    private val functions = mutableListOf(*functions)

    internal operator fun plusAssign(functions: Iterable<Function<*>>) {
        this.functions += functions
    }

    override fun iterator(): Iterator<Function<*>> {
        return functions.iterator()
    }
    
    internal operator fun get(functionName: String): Function<*> {
        return functions.firstOrNull { it.name == functionName } ?: throw FunctionNotFoundException(functionName)
    }
}