package quark.util.terminal

fun prompt(promptMessage: String = ""): String {
    println()
    print("$promptMessage > ")
    return readln()
}
