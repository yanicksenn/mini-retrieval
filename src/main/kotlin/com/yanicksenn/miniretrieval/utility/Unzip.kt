package com.yanicksenn.miniretrieval.utility

import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Path
import java.util.zip.ZipInputStream

/**
 * Unzips all files the target path.
 * @param targetPath Target path
 * @param charset Charset
 */
fun InputStream.unzipTo(targetPath: Path, charset: Charset = Charsets.UTF_8) {
    ZipInputStream(this, charset).use { inputStream ->
        var entry = inputStream.nextEntry
        while (entry != null) {
            val entryPath = targetPath.resolve(entry.name)

            // Ensure parent folder exists
            val entryParentPath = entryPath.parent
            val entryParentFile = entryParentPath.toFile()
            if (!entryParentFile.exists())
                entryParentFile.mkdirs()

            if (!entry.isDirectory) {
                val entryFile = entryPath.toFile()
                entryFile.outputStream().use { outputStream ->
                    inputStream.transferTo(outputStream)
                }
            }

            entry = inputStream.nextEntry
        }
    }
}