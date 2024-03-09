package quark.lang.instructions

interface Recordable {
    val header: ResultHeader
    val row: ResultRow
}