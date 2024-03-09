package quark.lang.instructions

import quark.lang.types.Thing

class ResultRow(private vararg val cells: Thing<*>) : Iterable<Thing<*>> {
    override fun iterator(): Iterator<Thing<*>> {
        return cells.iterator()
    }
}