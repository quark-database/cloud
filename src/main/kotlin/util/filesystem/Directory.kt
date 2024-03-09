package util.filesystem

import quark.util.filesystem.File
import quark.util.filesystem.renameFile
import util.filesystem.exceptions.*
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream
import kotlin.io.path.createDirectory
import kotlin.io.path.deleteExisting

class Directory(internal var path: Path) : Iterable<File> {
    override fun iterator(): Iterator<File> {
        return files.iterator()
    }

    fun hasDirectory(vararg filePath: String): Boolean {
        return Directory(getFilePath(*filePath)).exists
    }

    fun getFile(vararg filePath: String): File {
        return File(getFilePath(*filePath))
    }

    fun getFilePath(vararg filePath: String): Path {
        return Paths.get(path.toAbsolutePath().toString(), *filePath)
    }

    fun getAbsoluteFilePath(vararg filePath: String): String {
        return getFilePath(*filePath).toString()
    }

    fun getDirectory(vararg filePath: String): Directory {
        return Directory(Path.of(path.toAbsolutePath().toString(), *filePath))
    }

    val parent: Directory
        get() = Directory(path.parent)

    fun createFile(fileName: String): File {
        val file: File = File(path.resolve(fileName))
        file.create()

        return file
    }

    fun createFile(fileName: String, content: String) {
        val file: File = createFile(fileName)
        file.write(content)
    }

    fun createDirectory(directoryName: String): Directory {
        try {
            return Directory(Files.createDirectories(getFilePath(directoryName)))
        } catch (exception: IOException) {
            throw DirectoryCreateException(directoryName, exception)
        }
    }

    fun hasDirectory(directoryName: String): Boolean {
        val directoryPath = getFilePath(directoryName)

        return Files.exists(directoryPath) && Files.isDirectory(directoryPath)
    }

    val directories: List<Directory>
        get() {
            try {
                Files.list(path).use { paths ->
                    return paths.filter { path: Path -> path.toFile().isDirectory }
                        .map { path: Path -> Directory(path) }.toList()
                }
            } catch (exception: IOException) {
                throw DirectoryListException(this, exception)
            }
        }

    fun getSibling(siblingName: String): Directory {
        return Directory(path.resolveSibling(siblingName))
    }

    fun copy(destinationPath: Path) {
        destinationPath.toFile().mkdirs()

        paths
            .forEach { source: Path ->
                if (path.nameCount == source.nameCount) {
                    return@forEach
                }
                val destination = destinationPath.resolve(source.subpath(path.nameCount, source.nameCount))
                try {
                    val sourceFile = source.toFile()
                    val destinationFile = destination.toFile()
                    if (sourceFile.isDirectory) {
                        destinationFile.parentFile.mkdirs()
                    } else {
                        destinationFile.parentFile.mkdirs()
                    }

                    Files.copy(source, destination)
                } catch (exception: IOException) {
                    throw DirectoryCopyException(this, source, destination, exception)
                }
            }
    }

    fun moveTo(directoryName: String) {
        try {
            Files.move(path, parent.getFilePath(directoryName))
        } catch (exception: IOException) {
            throw DirectoryMoveException(this, directoryName, exception)
        }
    }

    fun create() {
        if(exists) {
            return
        }

        path.createDirectory()
    }

    fun delete() {
        if (!exists) {
            return
        }

        files.sorted(Comparator.reverseOrder()).forEach(File::delete)
        path.deleteExisting()
    }

    private val paths: Stream<Path>
        get() {
            try {
                return Files.walk(path)
            } catch (exception: IOException) {
                throw DirectoryWalkException(this, exception)
            }
        }

    val files: Stream<File>
        get() {
            return paths.map(::File)
        }

    val name: String
        get() = path.fileName.toString()

    val exists: Boolean
        get() {
            return Files.exists(path) && Files.isDirectory(path)
        }

    fun rename(newName: String) {
        this.path = renameFile(path, newName)
    }
}
