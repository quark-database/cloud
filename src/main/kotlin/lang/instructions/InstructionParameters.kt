package quark.lang.instructions

class InstructionParameters(vararg parameters: Pair<String, InstructionParameter>) : Iterable<Pair<String, InstructionParameter>> {
    private val names: List<String>
    private val parameters: List<InstructionParameter>

    init {
        parameters.unzip().let { (names, parameters) ->
            this.names = names
            this.parameters = parameters
        }

        // TODO
    }

    internal operator fun contains(name: String): Boolean {
        return name in names
    }

    override fun iterator(): Iterator<Pair<String, InstructionParameter>> {
        return (names zip parameters).iterator()
    }

    override fun toString(): String {
        return joinToString { (name, parameter) -> "$name = $parameter" }
    }
}