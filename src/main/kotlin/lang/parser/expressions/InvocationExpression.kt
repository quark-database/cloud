package quark.lang.parser.expressions

import quark.lang.functions.Function
import quark.lang.functions.FunctionArguments
import quark.lang.objects.QrkArray
import quark.lang.parser.exceptions.ParserException
import quark.lang.types.Thing
import quark.lang.types.builtin.ArrayType

class InvocationExpression(internal var function: Function<*>? = null) : Expression() {
    private val argumentExpressions = mutableListOf<Expression>()

    override fun evaluate(): Thing<*> {
        val parameters = function?.parameters
        val arguments = FunctionArguments()

        for((index, argumentExpression) in argumentExpressions.withIndex()) {
            val argument = argumentExpression.evaluate()
            val (name, parameter) = parameters?.get(index) ?: throw ParserException("The invocation expression must have a function.")

            if(parameter.varargs) {
                if(name !in arguments) {
                    arguments[name] = Thing(ArrayType, QrkArray())
                }

                arguments[name, ArrayType] += argument
            } else {
                arguments[name] = argument
            }
        }

        return function?.invoke(arguments) ?: throw ParserException("The invocation expression must have a function.")
    }

    internal operator fun plusAssign(expression: Expression) {
        if (expression == this) {
            throw ParserException("The invocation expression cannot contain itself as its argument.")
        }

        argumentExpressions += expression
    }
}