package util.filesystem.exceptions

abstract class FileException : RuntimeException {
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(message: String) : super(message)
}
