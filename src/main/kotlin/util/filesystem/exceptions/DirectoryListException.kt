package util.filesystem.exceptions

import util.filesystem.Directory

class DirectoryListException(
    directory: Directory,
    cause: Throwable
) : FileException("Can't get the list of files inside the directory ${directory.path}, because of $cause", cause)
