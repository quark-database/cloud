package quark.util.filesystem

import util.filesystem.exceptions.CannotCreateDirectoryException
import util.filesystem.exceptions.FileRenameException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

typealias IOFile = java.io.File

fun isFile(path: String): Boolean {
    return IOFile(path).isFile()
}

fun isDirectory(path: String): Boolean {
    return IOFile(path).isDirectory()
}

fun exists(path: String): Boolean {
    val directory = File(path)

    return directory.exists
}

fun missing(path: String): Boolean {
    return !exists(path)
}

fun createDirectories(vararg paths: String) {
    for (path in paths) {
        try {
            Files.createDirectory(Path.of(path))
        } catch (exception: IOException) {
            throw CannotCreateDirectoryException(path, exception)
        }
    }
}

fun createDirectoriesIfMissing(vararg paths: String) {
    for (path in paths) {
        if (missing(path)) {
            createDirectories(path)
        }
    }
}

@Suppress("unused")
fun deleteIfExists(vararg paths: String) {
    for (stringPath in paths.filter(::exists)) {
        if (isFile(stringPath)) {
            File(stringPath).delete()
        }

        if (isDirectory(stringPath)) {
            val directory = IOFile(stringPath)

            val contents = directory.listFiles()
            if (contents != null) {
                for (file in contents) {
                    if (!Files.isSymbolicLink(file.toPath())) {
                        deleteIfExists(file.absolutePath)
                    }
                }
            }

            directory.delete()
        }
    }
}

fun renameFile(path: Path, newName: String): Path {
    try {
        return Files.move(path, path.resolveSibling(newName))
    } catch (exception: IOException) {
        throw FileRenameException(path, newName, exception)
    }
}