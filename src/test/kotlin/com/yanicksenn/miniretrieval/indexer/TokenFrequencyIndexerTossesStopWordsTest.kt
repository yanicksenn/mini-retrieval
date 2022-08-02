package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.TokenizersBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File
import kotlin.test.assertFalse

class TokenFrequencyIndexerTossesStopWordsTest {

    @Test
    fun `ensure stop-words are tossed from the indices`() {
        val indexer = TokenFrequencyIndexer(
            TokenizersBuilder.build(),
            LexiconsBuilder.build(),
            StemmersBuilder.build(),
            StopListsBuilder.build()
        )

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
