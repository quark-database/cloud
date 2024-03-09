package quark.util.logging

class Logger(private val source: String, internal val threshold: LoggingLevel = LoggingLevel.TRACE) {
    fun log(loggingLevel: LoggingLevel, message: String) {
        if(loggingLevel >= threshold) {
            println("$loggingLevel   $source: $message")
        }
    }

    fun trace(message: String) = log(LoggingLevel.TRACE, message)
    fun debugging(message: String) = log(LoggingLevel.DEBUGGING, message)
    fun information(message: String) = log(LoggingLevel.INFORMATION, message)
    fun warning(message: String) = log(LoggingLevel.WARNING, message)
    fun error(message: String) = log(LoggingLevel.ERROR, message)
}