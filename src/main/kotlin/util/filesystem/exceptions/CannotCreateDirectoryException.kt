package util.filesystem.exceptions

class CannotCreateDirectoryException(
    directoryName: String,
    cause: Throwable
) : FileException("Cannot create a directory at '$directoryName', because of $cause.", cause)
