package com.yanicksenn.miniretrieval.utility

import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Returns a zip input stream of this file.
 * @param charset Charset
 */
fun File.asZipInputStream(charset: Charset = Charsets.UTF_8): ZipInputStream {
    return ZipInputStream(inputStream(), charset)
}

/**
 * Returns a sequence of wrapped zip entries.
 * The input stream provided by the entries
 * will automatically be closed.
 */
fun ZipInputStream.asSequence(): Sequence<StreamableZipEntry> {
    return sequence {
        use { inputStream ->
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