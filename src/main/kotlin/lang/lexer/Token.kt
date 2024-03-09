package quark.lang.lexer

data class Token(val name: String, val value: String) {
    override fun toString(): String {
        return "<$name = '$value'>"
    }
}
