package quark.util.reports

class Report(
    val context: String,
    val error: String,
    val advice: String,
    val steps: Array<String>) : Exception() {
    val guide by lazy { steps.joinToString("\n", transform = { "\t - $it;" }) }

    override val message: String
        get() = """
        |
        |❌  An error occurred in Quark.
        |
        |The context:  $context;
        |The error:    $error;
        |What to do:   $advice;
        |
        |Try following these steps:
        |$guide
        |
        |Doesn't work? Ask a question!
        |https://github.com/quark-database/cloud/issues
        |
        """.trimMargin()
}