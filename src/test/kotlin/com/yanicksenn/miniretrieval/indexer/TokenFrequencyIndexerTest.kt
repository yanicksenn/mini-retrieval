package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
        indexer.addToIndices("doc1.txt", "hello")
        assertTrue(indexer.indexedTokens().contains("hello"))
    }

    @Test
    fun `should contain token in indexed documents`() {
        indexer.addToIndices("doc1.txt", "hello")
        assertTrue(indexer.indexedDocuments().contains("doc1.txt"))
    }

    @Test
    fun `should not find tokens for unknown document`() {
        assertEquals(emptySet(), indexer.findTokensByDocument("doc1.txt"))
    }

    @Test
    fun `should find tokens for document`() {
        indexer.addToIndices("doc1.txt", "hello")
        indexer.addToIndices("doc1.txt", "world")
        indexer.addToIndices("doc2.txt", "hello")
        indexer.addToIndices("doc2.txt", "there")

        val tokens = indexer.findTokensByDocument("doc1.txt")
        assertEquals(setOf("hello", "world"), tokens)
    }

    @Test
    fun `should find no frequency for token in document`() {
        indexer.addToIndices("doc1.txt", "hello")
        assertEquals(0, indexer.findFrequency("doc2.txt", "world"))
    }

    @Test
    fun `should find frequency for token in document`() {
        indexer.addToIndices("doc1.txt", "hello")
        assertEquals(1, indexer.findFrequency("doc1.txt", "hello"))
    }

    @Test
    fun `should find summed up frequency for same token in document`() {
        indexer.addToIndices("doc1.txt", "hello")
        indexer.addToIndices("doc1.txt", "hello")
        indexer.addToIndices("doc1.txt", "hello")
        assertEquals(3, indexer.findFrequency("doc1.txt", "hello"))
    }

    @Test
    fun `should not find summed up frequency for same token in different documents`() {
        indexer.addToIndices("doc1.txt", "hello")
        indexer.addToIndices("doc1.txt", "world")
        indexer.addToIndices("doc2.txt", "hello")
        indexer.addToIndices("doc2.txt", "there")

        assertEquals(1, indexer.findFrequency("doc1.txt", "hello"))
        assertEquals(1, indexer.findFrequency("doc1.txt", "world"))

        assertEquals(1, indexer.findFrequency("doc2.txt", "hello"))
        assertEquals(1, indexer.findFrequency("doc2.txt", "there"))
    }

    @Test
    fun `should not find documents for unknown token`() {
        val documents = indexer.findDocumentsByToken("hello")
        assertEquals(0, documents.size)
    }

    @Test
    fun `should find documents for token`() {
        indexer.addToIndices("doc1.txt", "hello")
        indexer.addToIndices("doc1.txt", "world")
        indexer.addToIndices("doc2.txt", "hello")
        indexer.addToIndices("doc2.txt", "there")

        val documents = indexer.findDocumentsByToken("hello")
        assertEquals(setOf("doc1.txt", "doc2.txt"), documents)
    }
}