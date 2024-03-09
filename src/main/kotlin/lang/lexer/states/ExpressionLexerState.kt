package quark.lang.lexer.states

import quark.lang.lexer.Lexer
import quark.lang.lexer.LexerState
import quark.lang.lexer.exceptions.UnexpectedCharLexerException

class ExpressionLexerState(lexer: Lexer, override var next: LexerState) : LexerState(lexer, next) {
    override fun findTokens() {
        skipWhitespaces()

        lexer.now(when {
            atLowerLatin                      -> FunctionNameLexerState(lexer, next)
            atDigit || at('+', '-', '.') -> NumberLexerState(lexer, next)
            at('"')                      -> StringLexerState(lexer, next)

            else -> throw UnexpectedCharLexerException(this)
        })
    }
}