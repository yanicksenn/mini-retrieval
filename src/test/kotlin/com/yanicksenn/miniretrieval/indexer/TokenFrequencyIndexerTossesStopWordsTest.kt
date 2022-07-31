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
        assertDoesNotThrow { indexer.addDocumentToIndex(file.absolutePath, file.readText()) }

        indexer
            .assertDoesNotContainToken("as")
            .assertDoesNotContainToken("and")
            .assertDoesNotContainToken("the")
    }

    private fun TokenFrequencyIndexer.assertDoesNotContainToken(token: String): TokenFrequencyIndexer {
        assertFalse(indexedTokens().contains(token), "token $token should not be indexed")
        return this
    }
}
