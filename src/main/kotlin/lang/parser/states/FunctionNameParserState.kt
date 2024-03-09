package quark.lang.parser.states

import quark.lang.parser.Parser
import quark.lang.parser.exceptions.ParserException
import quark.lang.parser.expressions.InvocationExpression

class FunctionNameParserState(
    override val parser: Parser,
    private val invocation: InvocationExpression,
    private val afterInvocation: ParserState
) : ParserState(parser) {

    override fun parse() {
        if(!at("function name")) {
            throw ParserException("The invocation expression must start with a function name.")
        }

        invocation.function = parser.interpreter.getFunction(value)
        logger.trace("The function name is set to '$value'.")
        shift()

        parser.now(InvocationParserState(parser, invocation, afterInvocation))
    }
}