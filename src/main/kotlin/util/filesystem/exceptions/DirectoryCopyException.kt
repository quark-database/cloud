package util.filesystem.exceptions

import util.filesystem.Directory
import java.nio.file.Path

class DirectoryCopyException(
    directory: Directory, 
    source: Path, 
    destination: Path, 
    cause: Throwable
) : FileException("Can't copy a file '$source' from the directory '${directory.path}' to '$destination', because of $cause", cause)
