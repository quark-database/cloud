package quark.util.strings

import quark.util.collections.flip
import kotlin.math.max

private val ESCAPES = mapOf(
    """\t"""" to "\\t",
    """\n"""  to "\n",
    """\"""   to "\\",
)

private fun String.ellipsis(width: Int): String {
    val ellipsis = "..."

    if (length <= width) {
        return this
    }

    return substring(0, length - ellipsis.length) + ellipsis
}

fun String.padCenter(width: Int): String {
    val padding = max(0, width - length)
    val rightPadding = padding.floorDiv(2)
    val leftPadding = padding - rightPadding

    return (" " * leftPadding) + ellipsis(width) + (" " * rightPadding)
}

fun String.withPadding(padding: Int): String =
    padCenter(length + padding * 2)

operator fun String.times(count: Int): String {
    return repeat(count)
}

fun String.replaceMany(replacements: Map<String, String>): String {
    return replacements.entries.fold(this) { string, (entry, replacement) -> string.replace(entry, replacement) }
}

fun String.escape(): String {
    return replaceMany(ESCAPES)
}

fun String.sanitizeEscapes(): String {
    return replaceMany(ESCAPES.flip())
}

fun String.title(): String {
    if (isEmpty()) {
        return ""
    }

    return first().uppercase() + substring(1).lowercase()
}
