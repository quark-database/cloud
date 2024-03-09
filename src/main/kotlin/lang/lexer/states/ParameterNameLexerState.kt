package quark.lang.lexer.states

import quark.lang.lexer.Lexer
import quark.lang.lexer.LexerState
import quark.lang.lexer.exceptions.UnexpectedCharLexerException

class ParameterNameLexerState(lexer: Lexer) : LexerState(lexer, null) {
    override fun findTokens() {
        skipWhitespaces()

        when(char) {
            ',' -> shift()
            ';' -> {
                found("semicolon")
                return lexer.finish()
            }
        }

        memorizeName("parameter name")

        when(char) {
            '=' -> {
                shift()
                lexer.now(ExpressionLexerState(lexer, ParameterNameLexerState(lexer)))
            }

            else -> throw UnexpectedCharLexerException(this)
        }
    }
}