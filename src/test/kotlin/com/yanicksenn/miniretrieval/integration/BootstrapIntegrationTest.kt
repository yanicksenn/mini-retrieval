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
    fun `should bootstrap application`() {
        assertDoesNotThrow { runIntegrationTest("${documentsDirectory.absolutePath} \"who is max verstappen\"") }
    }
}