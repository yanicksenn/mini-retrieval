package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TokenFrequencyIndexerTest {

    private lateinit var indexer: TokenFrequencyIndexer

    @BeforeEach
    fun beforeEach() {
        indexer = TokenFrequencyIndexer()
    }

    @Test
    fun `should initially have no tokens indexed`() {
        assertEquals(0, indexer.indexedTokens().size)
    }

    @Test
    fun `should initially have no documents indexed`() {
        assertEquals(0, indexer.indexedDocuments().size)
    }

    @Test
    fun `should contain token in indexed tokens`() {
        indexer.add("doc1.txt", "hello")
        assertTrue(indexer.indexedTokens().contains("hello"))
    }

    @Test
    fun `should contain token in indexed documents`() {
        indexer.add("doc1.txt", "hello")
        assertTrue(indexer.indexedDocuments().contains("doc1.txt"))
    }

    @Test
    fun `should not find tokens for unknown document`() {
        assertEquals(emptyMap(), indexer.findTokensByDocument("doc1.txt"))
    }

    @Test
    fun `should find tokens for document`() {
        indexer.add("doc1.txt", "hello")
        indexer.add("doc1.txt", "world")
        indexer.add("doc2.txt", "hello")
        indexer.add("doc2.txt", "there")

        val tokens = indexer.findTokensByDocument("doc1.txt")
        assertEquals(mapOf("hello" to 1, "world" to 1), tokens)
    }

    @Test
    fun `should find no frequency for token in document`() {
        indexer.add("doc1.txt", "hello")
        assertEquals(0, indexer.findTokensByDocument("doc2.txt").getOrDefault("world", 0))
    }

    @Test
    fun `should find frequency for token in document`() {
        indexer.add("doc1.txt", "hello")
        assertEquals(1, indexer.findTokensByDocument("doc1.txt").getOrDefault("hello", 0))
    }

    @Test
    fun `should find summed up frequency for same token in document`() {
        indexer.add("doc1.txt", "hello")
        indexer.add("doc1.txt", "hello")
        indexer.add("doc1.txt", "hello")
        assertEquals(3, indexer.findTokensByDocument("doc1.txt").getOrDefault("hello", 0))
    }

    @Test
    fun `should not find summed up frequency for same token in different documents`() {
        indexer.add("doc1.txt", "hello")
        indexer.add("doc1.txt", "world")
        indexer.add("doc2.txt", "hello")
        indexer.add("doc2.txt", "there")

        assertEquals(1, indexer.findTokensByDocument("doc1.txt").getOrDefault("hello", 0))
        assertEquals(1, indexer.findTokensByDocument("doc1.txt").getOrDefault("world", 0))

        assertEquals(1, indexer.findTokensByDocument("doc2.txt").getOrDefault("hello", 0))
        assertEquals(1, indexer.findTokensByDocument("doc2.txt").getOrDefault("there", 0))
    }

    @Test
    fun `should not find documents for unknown token`() {
        val documents = indexer.findDocumentsByToken("hello")
        assertEquals(0, documents.size)
    }

    @Test
    fun `should find documents for token`() {
        indexer.add("doc1.txt", "hello")
        indexer.add("doc1.txt", "world")
        indexer.add("doc2.txt", "hello")
        indexer.add("doc2.txt", "there")

        val documents = indexer.findDocumentsByToken("hello")
        assertEquals(mapOf("doc1.txt" to 1, "doc2.txt" to 1), documents)
    }
}