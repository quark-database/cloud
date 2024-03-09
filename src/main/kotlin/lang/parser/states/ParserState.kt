package quark.lang.parser.states

import quark.lang.lexer.Token
import quark.lang.parser.Parser
import quark.util.classes.friendlyName
import quark.util.logging.Logger

abstract class ParserState(protected open val parser: Parser) {
    internal val name: String
        get() = friendlyName.replace(" parser state", "")

    protected val logger: Logger
        get() = parser.logger

    private val token: Token
        get() = parser.token

    protected val tokenName: String
        get() = token.name

    protected val value: String
        get() = token.value

    protected val unfinished
        get() = parser.unfinished

    abstract fun parse()

    internal fun shift() {
        parser.shift()
        logger.trace("The token caret is shifted to $tokenName = '$value'.")
    }

    protected fun at(tokenName: String, vararg more: String): Boolean {
        return this.tokenName == tokenName || this.tokenName in more
    }
}