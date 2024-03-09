package util.filesystem.exceptions

import quark.util.filesystem.File

class FileWriteException(
    file: File, 
    cause: Throwable
) : FileException("Can't write to a file '${file.path}', because of $cause", cause)
