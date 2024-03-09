package quark.util.filesystem

import util.filesystem.exceptions.*
import java.io.IOException
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.*


class File(val path: Path) : Comparable<File>, Appendable {
    private val iOFile: java.io.File = path.toFile()

    constructor(path: String) : this(Path.of(path))

    val exists: Boolean
        get() {
            return iOFile.exists() && iOFile.isFile
        }

    val name: String
        get() = iOFile.name

    val extension: String
        get() {
            val fileName = iOFile.name

            return fileName.substring(fileName.lastIndexOf('.') + 1)
        }

    fun hasExtension(extension: String): Boolean {
        return extension.equals(this.extension, ignoreCase = true)
    }

    fun read(): String {
        ensureExists()

        try {
            return Files.readString(path)
        } catch (exception: IOException) {
            throw FileReadException(this, exception)
        }
    }

    fun readLines(): List<String> {
        ensureExists()

        try {
            return Files.readAllLines(path)
        } catch (exception: IOException) {
            throw FileReadException(this, exception)
        }
    }

    val lines: Iterable<String>
        get() = readLines()

    fun write(text: String) {
        writeWithOptions(text, StandardOpenOption.TRUNCATE_EXISTING)
    }

    fun writeLines(iterable: Iterable<*>) {
        iterable.forEach { element: Any? -> append(element.toString()) }
    }

    fun append(text: String) {
        writeWithOptions(text, StandardOpenOption.APPEND)
    }

    private fun writeWithOptions(text: String, vararg options: StandardOpenOption) {
        try {
            create()
            Files.writeString(path, text, *options)
        } catch (exception: IOException) {
            throw FileWriteException(this, exception)
        }
    }

    val uri: URI
        get() = iOFile.toURI()

    val url: URL
        get() {
            try {
                return uri.toURL()
            } catch (exception: MalformedURLException) {
                throw FileURLException(this, exception)
            }
        }

    private fun ensureExists() {
        if (!exists) {
            throw FileNotFoundException(this)
        }
    }

    fun delete() {
        try {
            Files.deleteIfExists(path)
        } catch (exception: IOException) {
            throw util.filesystem.exceptions.FileDeleteException(this, exception)
        }
    }

    fun create() {
        if (exists) {
            return
        }

        try {
            iOFile.createNewFile()
        } catch (exception: IOException) {
            throw FileCreateException(this, exception)
        }
    }

    fun copy(destination: Path) {
        try {
            Files.copy(path, destination)
        } catch (exception: IOException) {
            throw FileCopyException(this, exception)
        }
    }

    override fun compareTo(other: File): Int {
        return iOFile.compareTo(other.iOFile)
    }

    override fun append(sequence: CharSequence): Appendable {
        Objects.requireNonNull(sequence, "Cannot append a null char sequence to a file")
        append(sequence.toString())
        return this
    }

    override fun append(sequence: CharSequence, start: Int, end: Int): Appendable {
        return append(sequence.subSequence(start, end))
    }

    override fun append(character: Char): Appendable {
        append(character.toString())
        return this
    }

    val nameWithoutExtension: String
        get() = name.removePrefix(extension)
}

fun createFile(path: String): File {
    val file = File(path)
    file.create()

    return file
}