package quark.lang.parser.expressions

import quark.lang.types.Thing

class LiteralExpression(private val value: Thing<*>) : Expression() {
    override fun evaluate(): Thing<*> = value
}