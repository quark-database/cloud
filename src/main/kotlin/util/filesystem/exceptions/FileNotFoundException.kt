package util.filesystem.exceptions

import quark.util.filesystem.File

class FileNotFoundException(file: File) : FileException("The file '${file.path}' does not exist.")
