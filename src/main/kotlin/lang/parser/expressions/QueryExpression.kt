package quark.lang.parser.expressions

import quark.lang.instructions.Instruction
import quark.lang.instructions.InstructionArguments

class QueryExpression {
    internal var instruction: Instruction? = null
    internal val argumentsMap = mutableMapOf<String, Expression>()

    internal val arguments: InstructionArguments
        get() = InstructionArguments(*argumentsMap.map { (name, expression) -> name to expression.evaluate() }.toTypedArray())

    internal operator fun set(argumentName: String, argumentExpression: Expression) {
        argumentsMap[argumentName] = argumentExpression
    }
}