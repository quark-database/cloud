package util.filesystem.exceptions

import quark.util.filesystem.File

class FileCreateException(
    file: File, 
    cause: Throwable
) : FileException("Can't create a file '${file.path}', because of $cause", cause)
