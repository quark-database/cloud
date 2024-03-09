package quark.lang.instructions

data class InstructionResult(
    internal val status: InstructionStatus,
    internal val message: InstructionResultMessage,
    internal val resultSet: ResultSet
) : Iterable<ResultRow> {
    override fun iterator(): Iterator<ResultRow> {
        return resultSet.iterator()
    }
}