package quark.lang.instructions

class ResultSet(private val header: ResultHeader, private vararg val rows: ResultRow) : Iterable<ResultRow> {
    override fun iterator(): Iterator<ResultRow> {
        return rows.iterator()
    }
}