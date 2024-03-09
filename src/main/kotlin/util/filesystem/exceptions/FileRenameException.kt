package util.filesystem.exceptions

import java.nio.file.Path

class FileRenameException(
    path: Path,
    newName: String,
    cause: Throwable
) : FileException("Can't rename the file $path to $newName, because of $cause", cause)
