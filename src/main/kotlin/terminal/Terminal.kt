package quark.terminal

import quark.lang.exceptions.LanguageException
import quark.lang.interpreter.Interpreter
import quark.util.strings.times
import quark.util.terminal.TerminalColor
import quark.util.terminal.paint

const val PROMPT_MARGIN: Int = 10

fun promptQueriesInfinitely(interpreter: Interpreter): Nothing {
    println(LOGO)
    while(true) {
        print("qql> ")
        val query = readln()
        println()

        try {
            val result = interpreter.execute(query)
            println("${result.status}: ${result.message}")
        } catch(exception: LanguageException) {
            println(exception.message)
            println(exception.stackTraceToString().paint(TerminalColor.YELLOW_BRIGHT))
        }

        print("\n" * PROMPT_MARGIN)
    }
}