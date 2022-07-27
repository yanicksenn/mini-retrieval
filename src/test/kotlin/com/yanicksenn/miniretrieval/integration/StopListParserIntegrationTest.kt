package com.yanicksenn.miniretrieval.integration

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File
import kotlin.test.assertTrue


class StopListParserIntegrationTest : AbstractIntegrationTest() {

    private val documentsDirectory = File("src/test/resources/documents")

    @Test
    fun `program parses english stop-list`() {
        assertDoesNotThrow { runIntegrationTest(documentsDirectory.absolutePath) }

        out.lines()
            .assertStopListWasParsed("english")
    }

    @Test
    fun `program parses german stop-list`() {
        assertDoesNotThrow { runIntegrationTest(documentsDirectory.absolutePath) }

        out.lines()
            .assertStopListWasParsed("german")
    }

    private fun List<String>.assertStopListWasParsed(language: String): List<String> {
        val condition = filter { it.contains("stop-list") }.any { it.contains(language) }
        assertTrue(condition, "$language stop-list was not parsed")
        return this
    }
}