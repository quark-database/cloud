package quark.lang.lexer

import quark.lang.lexer.exceptions.FailureLexerStateSwitchException
import quark.lang.lexer.exceptions.UnexpectedEndLexerException
import quark.lang.lexer.states.InstructionNameLexerState
import quark.util.logging.Logger
import quark.util.logging.LoggingLevel

class Lexer(private val query: String, loggingLevel: LoggingLevel = LoggingLevel.INFORMATION) {
    internal val logger = Logger("Lexer", loggingLevel)
    private var state: LexerState = InstructionNameLexerState(this)
    private var isLexing = true
    internal val tokens = mutableListOf<Token>()
    private var index = 0

    internal val char: Char
        get() {
            if(finished) {
                throw UnexpectedEndLexerException(state)
            }

            return query[index]
        }

    private val finished
        get() = index >= query.length

    internal val unfinished
        get() = !finished

    fun lex() {
        tokens.clear()

        logger.trace("Starting the lexing.")
        while(isLexing) {
            logger.trace("State $state is now in operation.")
            state.findTokens()
        }
        logger.trace("Lexing is complete, ${tokens.size} tokens found.")
    }

    internal fun finish() {
        logger.trace("The $state finished the lexing.")
        isLexing = false
    }

    internal fun now(state: LexerState) {
        logger.trace("Switched state to $state.")
        this.state = state
    }
    
    fun shift() {
        logger.trace("The character index is shifted.")
        index += 1

        if(unfinished) {
            logger.trace("The current character is now '$char'.")
        }
    }

    fun next() {
        logger.trace("Move to the next state $state -> ${state.next}.")
        this.state = state.next ?: throw FailureLexerStateSwitchException(state)
    }
}


