package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File
import kotlin.test.assertFalse

class SimpleIndexerTossesStopWordsTest {

    @Test
    fun `ensure stop-words are tossed from the indices`() {
        val indexer = SimpleIndexerBuilder.build()
        val documentsRoot = File("src/test/resources/documents")

        assertDoesNotThrow { indexer.addFilesToIndexRecursively(documentsRoot) }

        indexer
            .assertDoesNotContainToken("the")
            .assertDoesNotContainToken("as")
    }

    private fun IIndexer.assertDoesNotContainToken(token: String): IIndexer {
        assertFalse(indexedTokens().contains(token), "token $token should not be indexed")
        return this
    }
}
