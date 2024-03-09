package quark.lang.lexer.states

import quark.lang.lexer.Lexer
import quark.lang.lexer.LexerState

class FunctionNameLexerState(lexer: Lexer, override val next: LexerState) : LexerState(lexer, next) {
    override fun findTokens() {
        logger.trace("Skipping the whitespaces.")
        skipWhitespaces()

        logger.trace("Expecting the function name.")
        memorizeName("function name")

        when(char) {
            '('  -> lexer.now(FunctionArgumentLexerState(lexer, next))
            else -> lexer.next()
        }
    }
}