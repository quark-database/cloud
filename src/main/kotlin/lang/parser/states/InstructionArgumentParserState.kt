package quark.lang.parser.states

import quark.lang.parser.Parser
import quark.lang.parser.exceptions.ParserException
import quark.lang.parser.expressions.InvocationExpression
import quark.lang.parser.expressions.LiteralExpression
import quark.lang.types.Thing
import quark.lang.types.builtin.IntType
import quark.lang.types.builtin.NumberType
import quark.lang.types.builtin.StrType

class InstructionArgumentParserState(
    parser: Parser
) : ParserState(parser) {

    override fun parse() {
        if(at("semicolon")) {
            return parser.finish()
        }

        if(!at("parameter name")) {
            throw ParserException("A parameter name is expected, not $tokenName.")
        }

        logger.trace("Read the parser name: $value")
        val parameterName = value
        shift()

        parser.query[parameterName] = when(tokenName) {
            "string" -> {
                val string = LiteralExpression(Thing(StrType, value))
                shift()

                string
            }

            "int" -> {
                val int = LiteralExpression(Thing(IntType, value.toLongOrNull() ?: throw ParserException("The string '$value' is not an int.")))
                shift()

                int
            }

            "number" -> {
                val number = LiteralExpression(Thing(NumberType, value.toDoubleOrNull() ?: throw ParserException("The string '$value' is not a number.")))
                shift()

                number
            }

            "function name" -> {
                val invocation = InvocationExpression()
                parser.now(FunctionNameParserState(parser, invocation, this))

                invocation
            }

            else -> throw ParserException("An expression for the instruction parameter is expected, not $tokenName.")
        }
    }
}