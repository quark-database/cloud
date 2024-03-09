package quark.lang.lexer.states

import quark.lang.lexer.Lexer
import quark.lang.lexer.LexerState
import quark.lang.lexer.exceptions.UnexpectedCharLexerException


class StringLexerState(lexer: Lexer, next: LexerState) : LexerState(lexer, next) {
    private val quotationMark = '"'
    override fun findTokens() {
        logger.trace("Skipping whitespaces.")
        skipWhitespaces()

        if(!at(quotationMark)) {
            logger.trace("The string must start with a quotation mark.")
            throw UnexpectedCharLexerException(this)
        }
        shift()

        logger.trace("Memorizing the string content.")
        while(unfinished && !at(quotationMark)) {
            eat()
        }

        if(!at(quotationMark)) {
            logger.trace("The string must end with a quotation mark.")
            throw UnexpectedCharLexerException(this)
        }
        shift()
        found("string")
        lexer.next()
    }
}