package quark.lang.instructions

class ResultHeader(private vararg val columns: String) : Iterable<String> {
    override fun iterator(): Iterator<String> {
        return columns.iterator()
    }
}