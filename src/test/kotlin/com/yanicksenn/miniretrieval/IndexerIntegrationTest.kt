package com.yanicksenn.miniretrieval

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File
import kotlin.test.assertTrue


class IndexerIntegrationTest : AbstractIntegrationTest() {

    private val documentsDirectory = File("src/test/resources/documents")

    @Test
    fun `program iterates through files in documents directory`() {
        assertDoesNotThrow { runIntegrationTest(documentsDirectory.absolutePath) }

        out.lines()
            .assertDocumentWasVisited("doc-1.txt")
            .assertDocumentWasVisited("doc-2.txt")
            .assertDocumentWasVisited("doc-3.txt")
            .assertDocumentWasVisited("doc-4.txt")
    }

    private fun List<String>.assertDocumentWasVisited(name: String): List<String> {
        assertTrue(any { it.contains(name) }, "file $name was not visited")
        return this
    }
}