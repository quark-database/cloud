package util.filesystem.exceptions

class DirectoryCreateException(
    directoryName: String,
    cause: Throwable
) : FileException("Can't create a directory '$directoryName', because of $cause", cause)
