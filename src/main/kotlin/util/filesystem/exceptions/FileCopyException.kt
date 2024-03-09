package util.filesystem.exceptions

import quark.util.filesystem.File

class FileCopyException(
    file: File,
    cause: Throwable
) : FileException("Can't copy the file '${file.path}', because of $cause", cause)
