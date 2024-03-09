package util.filesystem.exceptions

import quark.util.filesystem.File

class FileURLException(
    file: File, 
    cause: Throwable
) : FileException("Can't get the URL from a file '${file.path}, because of $cause", cause)
