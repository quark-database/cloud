package quark.lang.parser.states

import quark.lang.parser.Parser
import quark.lang.parser.exceptions.ParserException
import quark.lang.parser.expressions.LiteralExpression
import quark.lang.types.Thing
import quark.lang.types.builtin.IntType
import quark.lang.types.builtin.NumberType
import quark.lang.types.builtin.StrType
import kotlin.collections.set

class InstructionNameParserState(parser: Parser) : ParserState(parser) {
    override fun parse() {
        if(!at("instruction name")) {
            throw ParserException("The instruction must start with its name, not $tokenName.")
        }

        val instruction = parser.interpreter.getInstruction(value)
        parser.query.instruction = instruction
        logger.trace("The instruction is now set to '${instruction.name}'.")
        shift()

        if(at("semicolon")) {
            logger.trace("The semicolon is met after the instruction name. The parsing is finished.")
            return parser.finish()
        }

        if(at("parameter name")) {
            logger.trace("The instruction has no general parameters, because a parameter name is met.")
            parser.now(InstructionArgumentParserState(parser))
            return
        }

        parser.query.argumentsMap[instruction.general] = when(tokenName) {
            "string"        -> LiteralExpression(Thing(StrType, value))
            "int"           -> LiteralExpression(Thing(IntType, value.toLongOrNull() ?: throw ParserException("The string '$value' is not an int.")))
            "number"        -> LiteralExpression(Thing(NumberType, value.toDoubleOrNull() ?: throw ParserException("The string '$value' is not a number.")))

            else            -> throw ParserException("A literal or semicolon is expected after the instruction name, not $tokenName.")
        }
        logger.trace("The general parameter of the instruction is now set to '$value'.")
        shift()

        if(at("semicolon")) {
            logger.trace("The semicolon is found after the general parameter. The parsing is finished.")
            return parser.finish()
        }

        if(!at("parameter name")) {
            throw ParserException("A colon is expected after the general argument, not $tokenName.")
        }

        logger.trace("The instruction header parsing is finished. Now switching to reading the instruction arguments.")
        parser.now(InstructionArgumentParserState(parser))
    }
}