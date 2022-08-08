package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TFIDFIndexTest {

    private lateinit var index: TFIDFIndex

    @BeforeEach
    fun beforeEach() {
        index = TFIDFIndex()
    }

    @Test
    fun `should initially have no tokens indexed`() {
        assertEquals(0, index.indexedTokens().size)
    }

    @Test
    fun `should initially have no documents indexed`() {
        assertEquals(0, index.indexedDocuments().size)
    }

    @Test
    fun `should contain token in indexed tokens`() {
        index.add("doc1.txt", "hello")
        assertTrue(index.indexedTokens().contains("hello"))
    }

    @Test
    fun `should contain token in indexed documents`() {
        index.add("doc1.txt", "hello")
        assertTrue(index.indexedDocuments().contains("doc1.txt"))
    }

    @Test
    fun `should not find tokens for unknown document`() {
        assertEquals(emptyMap(), index.findTokensByDocument("doc1.txt"))
    }

    @Test
    fun `should find tokens for document`() {
        index.add("doc1.txt", "hello")
        index.add("doc1.txt", "world")
        index.add("doc2.txt", "hello")
        index.add("doc2.txt", "there")

        val tokens = index.findTokensByDocument("doc1.txt")
        assertEquals(mapOf("hello" to 1, "world" to 1), tokens)
    }

    @Test
    fun `should find no frequency for token in document`() {
        index.add("doc1.txt", "hello")
        assertEquals(0, index.findTokensByDocument("doc2.txt").getOrDefault("world", 0))
    }

    @Test
    fun `should find frequency for token in document`() {
        index.add("doc1.txt", "hello")
        assertEquals(1, index.findTokensByDocument("doc1.txt").getOrDefault("hello", 0))
    }

    @Test
    fun `should find summed up frequency for same token in document`() {
        index.add("doc1.txt", "hello")
        index.add("doc1.txt", "hello")
        index.add("doc1.txt", "hello")
        assertEquals(3, index.findTokensByDocument("doc1.txt").getOrDefault("hello", 0))
    }

    @Test
    fun `should not find summed up frequency for same token in different documents`() {
        index.add("doc1.txt", "hello")
        index.add("doc1.txt", "world")
        index.add("doc2.txt", "hello")
        index.add("doc2.txt", "there")

        assertEquals(1, index.findTokensByDocument("doc1.txt").getOrDefault("hello", 0))
        assertEquals(1, index.findTokensByDocument("doc1.txt").getOrDefault("world", 0))

        assertEquals(1, index.findTokensByDocument("doc2.txt").getOrDefault("hello", 0))
        assertEquals(1, index.findTokensByDocument("doc2.txt").getOrDefault("there", 0))
    }

    @Test
    fun `should not find documents for unknown token`() {
        val documents = index.findDocumentsByToken("hello")
        assertEquals(0, documents.size)
    }

    @Test
    fun `should find documents for token`() {
        index.add("doc1.txt", "hello")
        index.add("doc1.txt", "world")
        index.add("doc2.txt", "hello")
        index.add("doc2.txt", "there")

        val documents = index.findDocumentsByToken("hello")
        assertEquals(mapOf("doc1.txt" to 1, "doc2.txt" to 1), documents)
    }
}