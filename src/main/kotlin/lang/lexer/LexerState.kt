package quark.lang.lexer

import quark.util.classes.friendlyName
import quark.util.logging.Logger
import quark.util.strings.extract

abstract class LexerState(protected var lexer: Lexer, open val next: LexerState?) {
    protected val logger = Logger("Lexer (${toString()})", lexer.logger.threshold)
    protected val buffer = StringBuilder()
    val char
        get() = lexer.char

    protected val unfinished
        get() = lexer.unfinished

    protected val atLowerLatin
        get() = char in 'a'..'z'

    protected val atUpperLatin
        get() = char in 'A'..'Z'

    protected val atDigit
        get() = char in '0'..'9'

    abstract fun findTokens()

    protected fun shift() = lexer.shift()

    protected fun at(char: Char, vararg more: Char) = this.char == char || this.char in more

    protected fun skipWhitespaces() {
        while (unfinished && at(' ', '\n', '\t')) {
            shift()
        }
    }

    protected fun eat() {
        memorize()
        shift()
    }

    private fun memorize() {
        logger.trace("The character is memorized into a buffer: \"$buffer\" += '$char'.")
        buffer.append(char)
    }

    protected fun memorizeName(tokenName: String) {
        skipWhitespaces()

        while(unfinished && (at(' ') || atLowerLatin)) {
            if(at(' ') && !buffer.endsWith(' ') || atLowerLatin) {
                memorize()
            }

            shift()
        }

        found(tokenName, String::trimEnd)
    }

    protected fun found(tokenName: String, bufferTransformer: String.() -> String = { this }) {
        val tokenValue = buffer.extract().let(bufferTransformer)

        lexer.tokens += Token(tokenName, tokenValue)
        logger.trace("A token $tokenName = '$tokenValue' is found. The tokens now are: ${lexer.tokens}")
    }

    final override fun toString(): String {
        return friendlyName.replace(" lexer state", "")
    }
}