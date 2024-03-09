package quark.lang.lexer.states

import quark.lang.lexer.Lexer
import quark.lang.lexer.LexerState
import quark.lang.lexer.exceptions.UnexpectedCharLexerException

class InstructionArgumentsLexerState(lexer: Lexer) : LexerState(lexer, null) {
    override fun findTokens() {
        skipWhitespaces()

        when(char) {
            ':' -> shift()
            ';' -> {
                found("semicolon")
                return lexer.finish()
            }
            else -> throw UnexpectedCharLexerException(this)
        }

        lexer.now(ParameterNameLexerState(lexer))
    }
}