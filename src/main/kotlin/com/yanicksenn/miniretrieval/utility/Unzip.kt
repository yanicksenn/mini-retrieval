package com.yanicksenn.miniretrieval.utility

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.lang.Thread.yield
import java.nio.charset.Charset
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Returns a sequence of wrapped zip entries.
 */
fun File.asSequence(charset: Charset = Charsets.UTF_8): Sequence<StreamableZipEntry> {
    return sequence {
        ZipInputStream(inputStream(), charset).use { inputStream ->
            var entry = inputStream.nextEntry
            while (entry != null) {
                if (!entry.isDirectory)
                    yield(StreamableZipEntry(inputStream, entry))

                entry = inputStream.nextEntry
            }
        }
    }
}

/**
 * Pairs the input stream and zip entry for easier usage.
 */
data class StreamableZipEntry(
    val inputStream: InputStream,
    val entry: ZipEntry) {

    /**
     * Unzips this entry into the given target path.
     * @param targetPath Target path
     */
    infix fun unzipTo(targetPath: Path) {
        val entryPath = targetPath.resolve(entry.name)
        val entryFile = entryPath.toFile()
        entryFile.createParentDirectories()
        entryFile.outputStream().use { outputStream ->
            inputStream.transferTo(outputStream)
        }
    }

    private fun File.createParentDirectories() {
        if (!parentFile.exists())
            parentFile.mkdirs()
    }
}