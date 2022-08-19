package com.yanicksenn.miniretrieval.utility

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UnzipTest {

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `should unzip a zip-file`() {
        val zipFile = javaClass.getResourceAsStream("/com/yanicksenn/miniretrieval/utility/example.zip")!!
        zipFile.unzipTo(tempDir)

        assertFileWithContentExists("File1.txt", "File 1")
        assertFileWithContentExists("File2.txt", "File 2")
        assertFileWithContentExists("Folder/File3.txt", "File 3")
    }

    private fun assertFileWithContentExists(fileName: String, content: String) {
        val file1 = tempDir.resolve(fileName).toFile()
        assertTrue(file1.exists())
        assertEquals(content, file1.readText())
    }
}