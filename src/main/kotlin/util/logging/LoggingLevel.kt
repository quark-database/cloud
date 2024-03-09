package quark.util.logging

import quark.util.strings.padCenter
import quark.util.terminal.TerminalColor
import quark.util.terminal.paint


private const val padding = 3
private val longest: LoggingLevel
    get() = LoggingLevel.entries.maxBy { it.name }


enum class LoggingLevel(
    private val foregroundColor: TerminalColor,
    private val backgroundColor: TerminalColor,
) {
    TRACE(TerminalColor.BLACK_BRIGHT, TerminalColor.BLACK_BACKGROUND),
    DEBUGGING(TerminalColor.WHITE, TerminalColor.BLACK_BACKGROUND),
    INFORMATION(TerminalColor.CYAN, TerminalColor.BLACK_BACKGROUND),
    WARNING(TerminalColor.BLACK, TerminalColor.YELLOW_BACKGROUND),
    ERROR(TerminalColor.WHITE_BRIGHT, TerminalColor.RED_BACKGROUND);

    override fun toString(): String {
        return name
            .padCenter(longest.name.length + 2 * padding)
            .paint(foregroundColor, backgroundColor)
    }
}