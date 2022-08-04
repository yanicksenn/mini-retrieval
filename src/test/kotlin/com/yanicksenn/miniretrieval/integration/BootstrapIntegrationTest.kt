package com.yanicksenn.miniretrieval.integration

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File


/**
 * This class contains simple bootstrap integration tests.
 */
class BootstrapIntegrationTest : AbstractIntegrationTest() {

    private val documentsDirectory = File("src/test/resources/documents")

    @Test
    @Disabled
    fun `program iterates through files in documents directory`() {
        assertDoesNotThrow { runIntegrationTest(documentsDirectory.absolutePath) }
    }
}