package com.yanicksenn.miniretrieval.utility

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UnzipTest {

    @TempDir
    lateinit var documentRoot: Path

    @Test
    fun `should unzip a zip-file`() {
        val zipFile = copyTempResource("/com/yanicksenn/miniretrieval/utility/example.zip")
        val targetPath = documentRoot.resolve("example")
        zipFile.unzipTo(targetPath)

        targetPath.assertFileWithContentExists("File1.txt", "File 1")
        targetPath.assertFileWithContentExists("File2.txt", "File 2")
        targetPath.assertFileWithContentExists("Folder/File3.txt", "File 3")
    }

    private fun Path.assertFileWithContentExists(fileName: String, content: String) {
        val file1 = resolve(fileName).toFile()
        assertTrue(file1.exists())
        assertEquals(content, file1.readText())
    }

    /**
     * Copy a resource to a temporary location to be able
     * fully interact with it.
     * @param resourceName Name of the resource
     */
    private fun copyTempResource(resourceName: String): File {
        val inputStream = javaClass.getResourceAsStream(resourceName)!!
        val fileName = resourceName.split("/").last()
        val file = documentRoot.resolve(fileName).toFile()
        file.createNewFile()
        file.outputStream().use { inputStream.transferTo(it) }
        return file
    }
}