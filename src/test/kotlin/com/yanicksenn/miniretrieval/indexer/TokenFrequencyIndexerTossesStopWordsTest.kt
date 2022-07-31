package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File
import kotlin.test.assertFalse

class TokenFrequencyIndexerTossesStopWordsTest {

    @Test
    fun `ensure stop-words are tossed from the indices`() {
        val indexer = TokenFrequencyIndexerBuilder.build()
        val file = File("src/test/resources/documents/google.txt")
        assertDoesNotThrow { indexer.addFileToIndex(file) }

        indexer
            .assertDoesNotContainToken("search")
            .assertDoesNotContainToken("hassan")
            .assertDoesNotContainToken("page")
    }

    private fun TokenFrequencyIndexer.assertDoesNotContainToken(token: String): TokenFrequencyIndexer {
        assertFalse(indexedTokens().contains(token), "token $token should not be indexed")
        return this
    }
}