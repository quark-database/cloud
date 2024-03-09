package quark.lang.parser.expressions

import quark.lang.types.Thing

abstract class Expression {
    internal abstract fun evaluate(): Thing<*>
}