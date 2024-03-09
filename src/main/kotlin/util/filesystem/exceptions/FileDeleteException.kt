package util.filesystem.exceptions

import quark.util.filesystem.File

class FileDeleteException(
    file: File, 
    cause: Throwable
) : FileException("Can't delete a file ${file.path}, because of $cause", cause)
