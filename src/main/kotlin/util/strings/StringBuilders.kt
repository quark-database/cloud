package quark.util.strings

fun StringBuilder.extract(): String {
    val builderContent = this.toString()
    this.clear()

    return builderContent
}