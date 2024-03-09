package util.filesystem.exceptions

import util.filesystem.Directory

class DirectoryWalkException(
    directory: Directory,
    cause: Throwable
) : FileException("Can't walk files inside the directory ${directory.path}, because of $cause", cause)
