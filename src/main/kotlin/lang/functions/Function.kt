package quark.lang.functions

import quark.dbms.tables.columns.Column
import quark.lang.NativeType
import quark.lang.functions.exceptions.FunctionArgumentException
import quark.lang.functions.exceptions.FunctionArgumentMissingException
import quark.lang.functions.exceptions.FunctionArgumentTypeMismatchException
import quark.lang.hints.FunctionHint
import quark.lang.objects.QrkArray
import quark.lang.objects.createQrkArray
import quark.lang.types.Thing
import quark.lang.types.Type
import quark.lang.types.builtin.ArrayType
import quark.lang.types.builtin.ColumnType
import quark.lang.types.builtin.PropertyType
import quark.lang.types.builtin.StrType
import quark.util.classes.friendlyName
import quark.util.terminal.TerminalColor


abstract class Function<R>(private val returnType: Type<R>, vararg parameters: Pair<String, FunctionParameter<*>>) where R : NativeType<R> {
    internal val parameters = FunctionParameters(*parameters)
    internal open val name by lazy { friendlyName.replace(" function", "") }

    operator fun invoke(arguments: FunctionArguments): Thing<R> {
        addEmptyVarargs(arguments)
        ensureArgumentsAreValid(arguments)
        return Thing(returnType, onInvoke(arguments))
    }

    operator fun invoke(vararg arguments: Pair<String, Thing<*>>): Thing<R> {
        return invoke(FunctionArguments(*arguments))
    }

    protected abstract fun onInvoke(arguments: FunctionArguments): R

    private fun addEmptyVarargs(arguments: FunctionArguments) {
        if (parameters.hasVarargs && !arguments.contains(parameters.varargs)) {
            arguments[parameters.varargs] = Thing(ArrayType, QrkArray())
        }
    }

    private fun ensureArgumentsAreValid(arguments: FunctionArguments) {
        for ((name, value) in arguments) {
            if (name !in parameters) {
                throw FunctionArgumentException("The parameter '$name' is not in parameter list: $this($parameters).")
            }

            val parameter = parameters[name]

            if (!(value instanceOf parameter.type)) {
                val hint = FunctionHint(this, "The parameter $name must be a ${parameter.type}, not ${value.type}.", '~', TerminalColor.PURPLE_BRIGHT)

                for (argument in arguments) {
                    hint.appendArgument(argument, name == argument.first)
                }

                throw FunctionArgumentTypeMismatchException(hint)
            }

            arguments[name] = parameter.type.cast(value)
        }

        if (parameters.any { (name, parameter) -> parameter.required && name !in arguments }) {
            val hint = FunctionHint(this, "Add required arguments:", '+', TerminalColor.GREEN)

            for((name, parameter) in parameters.positional) {
                if(name in arguments) {
                    hint += name to arguments[name]
                } else {
                    hint *= name to parameter.example
                }
            }

            if(parameters.hasVarargs && !arguments[parameters.varargs, ArrayType].empty) {
                hint += arguments[parameters.varargs, ArrayType].joinToString(", ")
            }

            hint.close()
            throw FunctionArgumentMissingException(hint)
        }
    }

    override fun toString(): String {
        return "$name($parameters)"
    }
}


internal inline fun <reified T> createColumnFunction(columnType: Type<T>): Function<Column> where T : NativeType<T> {
    return object : Function<Column>(
        ColumnType,
        "column name" to FunctionParameter(StrType, example = columnType.columnNameExample),
        "parameters" to FunctionParameter(ArrayType, varargs = true, example = createQrkArray(ColumnType))
    ) {
        override val name by columnType::name

        override fun onInvoke(arguments: FunctionArguments): Column {
            val columnName = arguments["column name", StrType]
            val parameters = arguments.getArray("parameters", PropertyType)

            return Column(columnName, columnType, *parameters)
        }
    }
}