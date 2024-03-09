package quark.lang.parser

import quark.lang.instructions.Instruction
import quark.lang.instructions.InstructionArguments
import quark.lang.interpreter.Interpreter
import quark.lang.lexer.Token
import quark.lang.parser.exceptions.ParserException
import quark.lang.parser.expressions.QueryExpression
import quark.lang.parser.states.InstructionNameParserState
import quark.lang.parser.states.ParserState
import quark.util.classes.friendlyName
import quark.util.logging.Logger
import quark.util.logging.LoggingLevel

class Parser(private val tokens: List<Token>, internal val interpreter: Interpreter, loggingLevel: LoggingLevel = LoggingLevel.INFORMATION) {
    private var state: ParserState = InstructionNameParserState(this)
    internal val query = QueryExpression()
    private var isParsing = true
    private var index = 0
    internal val logger = Logger("Parser", loggingLevel)

    internal val token: Token
        get() = tokens.getOrNull(index) ?: throw ParserException("The token index $index is out of tokens range ($tokens).")

    internal val value: String
        get() = token.value

    internal val unfinished
        get() = index < tokens.size

    internal var instruction: Instruction
        get() = query.instruction ?: throw ParserException("The instruction in parser was not set.")
        set(newInstruction) {
            query.instruction = newInstruction
        }

    internal val arguments: InstructionArguments
        get() = query.arguments

    internal fun now(state: ParserState) {
        this.state = state
        logger.trace("The state is switched to ${state.name}.")
    }

    internal fun shift() {
        index += 1
    }

    internal fun parse() {
        while(isParsing) {
            logger.trace("The state ${state.name} will act.")
            state.parse()
        }

        logger.trace("The parsing is finished.")
    }

    internal fun finish() {
        logger.trace("The ${state.friendlyName} marked the parser as finished.")
        isParsing = false
    }
}