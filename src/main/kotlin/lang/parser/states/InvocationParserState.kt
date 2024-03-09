package quark.lang.parser.states

import quark.lang.parser.Parser
import quark.lang.parser.exceptions.ParserException
import quark.lang.parser.expressions.InvocationExpression
import quark.lang.parser.expressions.LiteralExpression
import quark.lang.types.Thing
import quark.lang.types.builtin.IntType
import quark.lang.types.builtin.NumberType
import quark.lang.types.builtin.StrType

class InvocationParserState(
    override val parser: Parser,
    private val invocation: InvocationExpression,
    private val afterInvocation: ParserState
) : ParserState(parser) {

    override fun parse() {
        while(unfinished) {
            if(at("function name")) {
                val expression = InvocationExpression()
                invocation += expression

                return parser.now(FunctionNameParserState(parser, expression, this))
            }

            invocation += when(tokenName) {
                "string" -> {
                    logger.trace("A string is found.")
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
                    val number = LiteralExpression(
                        Thing(
                            NumberType,
                            value.toDoubleOrNull() ?: throw ParserException("The string '$value' is not a number.")
                        )
                    )
                    shift()

                    number
                }

                "argument list end" -> {
                    parser.now(afterInvocation)
                    shift()

                    break
                }

                else -> throw ParserException("A function cannot take $tokenName as an argument.")
            }
        }
    }
}