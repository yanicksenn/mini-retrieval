package com.yanicksenn.miniretrieval

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertTrue

class BootstrapIntegrationTest : AbstractIntegrationTest() {

    @TempDir
    lateinit var documentsDirectory: File

    @BeforeEach
    fun beforeEach() {
        File(documentsDirectory, "doc-1.txt").createNewFile()
        File(documentsDirectory, "doc-2.txt").createNewFile()
        File(documentsDirectory, "doc-3.txt").createNewFile()
    }

    @Test
    fun `program iterates through files in documents directory`() {
        assertDoesNotThrow { runIntegrationTest(documentsDirectory.absolutePath) }

        val outLines = out.lines()
        assertTrue(outLines.any { it.contains("doc-1.txt") }, "doc-1 was not visited")
        assertTrue(outLines.any { it.contains("doc-2.txt") }, "doc-2 was not visited")
        assertTrue(outLines.any { it.contains("doc-3.txt") }, "doc-3 was not visited")
    }
}