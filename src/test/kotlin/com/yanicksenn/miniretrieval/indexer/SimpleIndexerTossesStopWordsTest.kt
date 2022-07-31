package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File
import kotlin.test.assertFalse

class SimpleIndexerTossesStopWordsTest {

    @Test
    fun `ensure stop-words are tossed from the indices`() {
        val indexer = SimpleTokenFrequencyIndexerBuilder.build()
        val documentsRoot = File("src/test/resources/documents")

        assertDoesNotThrow { indexer.addFilesToIndexRecursively(documentsRoot) }

        indexer
            .assertDoesNotContainToken("the")
            .assertDoesNotContainToken("as")
    }

    private fun ITokenFrequencyIndexer.assertDoesNotContainToken(token: String): ITokenFrequencyIndexer {
        assertFalse(indexedTokens().contains(token), "token $token should not be indexed")
        return this
    }
}
