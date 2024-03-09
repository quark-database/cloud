package quark.lang.lexer.states

import quark.lang.lexer.Lexer
import quark.lang.lexer.LexerState

class FunctionArgumentLexerState(lexer: Lexer, next: LexerState) : LexerState(lexer, next) {
    override fun findTokens() {
        skipWhitespaces()

        logger.trace("Expecting a function argument or argument list end.")
        when(char) {
            '(', ',' -> shift()
            ')' -> {
                shift()
                found("argument list end")
                lexer.next()
            }

            else -> lexer.now(ExpressionLexerState(lexer, this))
        }
    }
}