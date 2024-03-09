package util.filesystem.exceptions

import util.filesystem.Directory

class DirectoryMoveException(
    directory: Directory, 
    directoryName: String, 
    cause: Throwable
) : FileException("Can't move the directory ${directory.path} to $directoryName, because of $cause", cause)
