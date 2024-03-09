package quark.util.text

import quark.util.strings.times
import quark.util.terminal.TerminalColor
import quark.util.terminal.coloredLength
import quark.util.terminal.paint

open class CodeHint(private val hintMessage: String, private val char: Char, private val color: TerminalColor) {
    protected val codeBuilder = StringBuilder()
    private val highlightRegions = mutableListOf<Pair<Int, Int>>()

    internal operator fun plusAssign(code: String) {
        codeBuilder.append(code)
    }

    internal operator fun timesAssign(code: String) {
        highlightRegions += codeBuilder.toString().coloredLength to code.coloredLength
        codeBuilder.append(code.paint(color))
    }

    override fun toString(): String {
        return """
        ${"[ $char ]".paint(color)} $hintMessage
        $codeBuilder
        $underline
        """.trimIndent()
    }

    private val underline: String
        get() {
            val underlineBuilder = StringBuilder(" " * codeBuilder.toString().coloredLength)

            for ((regionStart, regionLength) in highlightRegions) {
                val regionUnderline = "$char" * regionLength
                underlineBuilder.replace(regionStart, regionStart + regionLength, regionUnderline)
            }

            return underlineBuilder.toString().paint(color)
        }
}