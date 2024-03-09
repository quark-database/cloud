package quark.lang.lexer.states

import quark.lang.lexer.Lexer
import quark.lang.lexer.LexerState

class InstructionNameLexerState(lexer: Lexer) : LexerState(lexer, null) {
    override fun findTokens() {
        logger.trace("Skipping the whitespaces.")
        skipWhitespaces()

        logger.trace("Expecting the instruction name.")
        memorizeName("instruction name")

        when(char) {
            ':' -> {
                lexer.now(InstructionArgumentsLexerState(lexer))
            }
            ';' -> {
                found("semicolon")
                lexer.finish()
            }
            else -> {
                lexer.now(ExpressionLexerState(lexer, InstructionArgumentsLexerState(lexer)))
            }
        }
    }
}