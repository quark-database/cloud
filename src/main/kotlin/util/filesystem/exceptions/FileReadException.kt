package util.filesystem.exceptions

import quark.util.filesystem.File

class FileReadException(
    file: File,
    cause: Throwable
) : FileException("Can't read a file '${file.path}', because of $cause", cause)
