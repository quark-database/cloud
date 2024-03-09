package quark.lang.hints

import quark.lang.functions.Function
import quark.lang.types.Thing
import quark.util.strings.withPadding
import quark.util.terminal.TerminalColor
import quark.util.terminal.paint
import quark.util.text.CodeHint

class FunctionHint(
    private val function: Function<*>,
    hintMessage: String,
    char: Char,
    color: TerminalColor
) : CodeHint(hintMessage, char, color) {
    private val hasNoArguments
        get() = codeBuilder.contentEquals(function.name)

    init {
        this += function.name
    }

    internal fun appendArgument(argument: Pair<String, Thing<*>>, highlighted: Boolean) {
        if(highlighted) {
            this *= argument
        } else {
            this += argument
        }
    }

    internal operator fun timesAssign(argument: Pair<String, Thing<*>>) {
        appendPunctuationMarks()
        this *= argument.second.toString()
    }

    internal operator fun plusAssign(argument: Pair<String, Thing<*>>) {
        appendPunctuationMarks()
        this += argument.second.toString()
    }

    internal fun close() {
        this += ")"
    }

    private fun appendParameterName(parameterName: String) {
        this += parameterName.withPadding(1).paint(TerminalColor.BLACK, TerminalColor.BLACK_BACKGROUND_BRIGHT)
        this += " "
    }

    private fun appendPunctuationMarks() {
        if(hasNoArguments) {
            this += "("
        } else {
            this += ", "
        }
    }
}