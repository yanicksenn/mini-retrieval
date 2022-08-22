package com.yanicksenn.miniretrieval.utility

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UnzipTest {

    @TempDir
    lateinit var documentRoot: Path

    @Test
    fun `should iterate through all entries`() {
        copyTempResource("/com/yanicksenn/miniretrieval/utility/example.zip")
            .asSequence()
            .map { it.entry.name }
            .toHashSet()
            .assertContains("File1.txt")
            .assertContains("File2.txt")
            .assertContains("Folder/File3.txt")
    }

    @Test
    fun `should unzip a zip-file`() {
        val targetPath = documentRoot.resolve("example")
        copyTempResource("/com/yanicksenn/miniretrieval/utility/example.zip")
            .asSequence()
            .forEach { it unzipTo targetPath }

        targetPath.assertFileWithContentExists("File1.txt", "File 1")
        targetPath.assertFileWithContentExists("File2.txt", "File 2")
        targetPath.assertFileWithContentExists("Folder/File3.txt", "File 3")
    }

    @Test
    fun `should partially unzip a zip-file`() {
        val targetPath = documentRoot.resolve("example")
        copyTempResource("/com/yanicksenn/miniretrieval/utility/example.zip")
            .asSequence()
            .filter { it.entry.name.contains("File3") }
            .forEach { it unzipTo targetPath }

        targetPath.assertFileDoesNotExist("File1.txt")
        targetPath.assertFileDoesNotExist("File2.txt")
        targetPath.assertFileWithContentExists("Folder/File3.txt", "File 3")
    }


    private fun Path.assertFileWithContentExists(fileName: String, content: String) {
        val file = resolve(fileName).toFile()
        assertTrue(file.exists())
        assertEquals(content, file.readText())
    }

    private fun Path.assertFileDoesNotExist(fileName: String) {
        val file = resolve(fileName).toFile()
        assertFalse(file.exists())
    }

    private fun Set<String>.assertContains(name: String): Set<String> {
        assertTrue(contains(name))
        return this
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