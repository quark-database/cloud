package quark.lang.instructions

import quark.lang.NativeType
import quark.lang.instructions.exceptions.InstructionResultException
import quark.lang.interpreter.Interpreter

class InstructionResultBuilder(private val interpreter: Interpreter) {
    internal var status: InstructionStatus? = null
    internal var message: String? = null
    private var header: ResultHeader = ResultHeader()
    private val resultSet = mutableListOf<ResultRow>()

    internal inline operator fun <reified T> plusAssign(row: Array<T>) where T : NativeType<T> {
        resultSet += ResultRow(*row.map(interpreter::wrap).toTypedArray())
    }

    internal operator fun plusAssign(recordable: Recordable) {
        header = recordable.header
        resultSet += recordable.row
    }

    internal operator fun plusAssign(recordables: Iterable<Recordable>) {
        recordables.forEach(::plusAssign)
    }

    internal fun build(): InstructionResult {
        return InstructionResult(
            status ?: throw InstructionResultException("The status in the instruction result builder must be set."),
            message ?: throw InstructionResultException("The message in the instruction result builder must be set."),
            ResultSet(header, *resultSet.toTypedArray())
        )
    }
}