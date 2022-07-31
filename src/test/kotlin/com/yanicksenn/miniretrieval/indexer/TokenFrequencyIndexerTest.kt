package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TokenFrequencyIndexerTest {

    private lateinit var indexer: TokenFrequencyIndexer

    @BeforeEach
    fun beforeEach() {
        indexer = TokenFrequencyIndexerBuilder.build()
        val file = File("src/test/resources/documents/google.txt")

        assertDoesNotThrow { indexer.addFileToIndex(file) }
    }

    @Test
    fun `ensure that the token-frequency matches in both indices`() {
        val expectedDocuments = indexer.indexedDocuments()
        val expectedTokens = indexer.indexedTokens()

        for (document in expectedDocuments) {
            for (token in expectedTokens) {
                val docsByTokenAmount = indexer.findDocumentsByToken(token)[document] ?: 0
                val tokenByDocsAmount = indexer.findTokensByDocument(document)[token] ?: 0

                assertEquals(docsByTokenAmount, tokenByDocsAmount)
            }
        }
    }

    @Test
    @Disabled
    fun `ensure all tokens were found`() {
        val actualTokens = indexer.indexedTokens()
        val expectedTokens = File("src/test/resources/indexer/SimpleIndexer/tokens.txt").readLines()

        assertEquals(expectedTokens.size, actualTokens.size,
            "amount of tokens should be ${expectedTokens.size} but was ${actualTokens.size}")

        for (expectedToken in expectedTokens)
            assertTrue(actualTokens.contains(expectedToken), "token $expectedToken was not found in indexer")
    }

    @Test
    @Disabled
    fun updateTokensFile() {
        val tokensFile = File("src/test/resources/indexer/SimpleIndexer/tokens.txt")
        tokensFile.writeText("")

        indexer.indexedTokens().sorted().forEach {
            tokensFile.appendText(it + System.lineSeparator())
        }
    }
}