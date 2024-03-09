package quark.lang.instructions

import quark.lang.types.Type

data class InstructionParameter(
    val type: Type<*>?,
    val general: Boolean = false,
    val required: Boolean = true
) {
    private val optional: Boolean
        get() = !required

    override fun toString(): String {
        return "${type?.toString() ?: "any"}${if(optional) " (optional)" else ""}"
    }
}