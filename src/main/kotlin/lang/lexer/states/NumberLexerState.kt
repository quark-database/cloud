package quark.lang.lexer.states

import quark.lang.lexer.Lexer
import quark.lang.lexer.LexerState
import quark.lang.lexer.exceptions.UnexpectedCharLexerException

class NumberLexerState(lexer: Lexer, next: LexerState) : LexerState(lexer, next) {
    override fun findTokens() {
        skipWhitespaces()

        when(char) {
            '+' -> shift()
            '-' -> eat()
        }

        while(unfinished && (at('.') || atDigit)) {
            if(at('.') && buffer.contains(".")) {
                throw UnexpectedCharLexerException(this)
            }

            eat()
        }

        found(if(buffer.contains(".")) "number" else "int")
        lexer.next()
    }
}
