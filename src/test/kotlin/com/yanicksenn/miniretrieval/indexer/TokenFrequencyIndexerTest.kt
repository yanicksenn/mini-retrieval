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
    fun `should contain no occurrence of token in document`() {
        val tokensByDocument = indexer.findTokensByDocument("doc1.txt")
        assertEquals(0, tokensByDocument.size)
    }

    @Test
    fun `should contain exactly one occurrence of token in document`() {
        indexer.addToIndices("doc1.txt", "hello")

        val tokensByDocument = indexer.findTokensByDocument("doc1.txt")
        assertEquals(1, tokensByDocument["hello"])
    }

    @Test
    fun `should contain exactly n occurrences of token in document`() {
        indexer.addToIndices("doc1.txt", "hello")
        indexer.addToIndices("doc1.txt", "hello")
        indexer.addToIndices("doc1.txt", "hello")

        val tokensByDocument = indexer.findTokensByDocument("doc1.txt")
        assertEquals(3, tokensByDocument["hello"])
    }


    @Test
    fun `should not contain document with token`() {
        val documentsByToken = indexer.findDocumentsByToken("hello")
        assertEquals(0, documentsByToken.size)
    }

    @Test
    fun `should contain document with token`() {
        indexer.addToIndices("doc1.txt", "hello")

        val documentsByToken = indexer.findDocumentsByToken("hello")
        assertTrue(documentsByToken.contains("doc1.txt"))
    }

    @Test
    fun `should not sum up same token in different documents`() {
        indexer.addToIndices("doc1.txt", "hello")
        indexer.addToIndices("doc1.txt", "world")
        indexer.addToIndices("doc2.txt", "hello")
        indexer.addToIndices("doc2.txt", "there")

        val tokensDoc1 = indexer.findTokensByDocument("doc1.txt")
        assertEquals(1, tokensDoc1["hello"])
        assertEquals(1, tokensDoc1["world"])

        val tokensDoc2 = indexer.findTokensByDocument("doc2.txt")
        assertEquals(1, tokensDoc2["hello"])
        assertEquals(1, tokensDoc2["there"])
    }

    @Test
    fun `should find all document with same token`() {
        indexer.addToIndices("doc1.txt", "hello")
        indexer.addToIndices("doc1.txt", "world")
        indexer.addToIndices("doc2.txt", "hello")
        indexer.addToIndices("doc2.txt", "there")
        indexer.addToIndices("doc3.txt", "goodbye")

        val documentsHello = indexer.findDocumentsByToken("hello")
        assertTrue(documentsHello.contains("doc1.txt"))
        assertTrue(documentsHello.contains("doc2.txt"))
        assertFalse(documentsHello.contains("doc3.txt"))
    }

    @Test
    fun `should find all tokens of document`() {
        val tokens = "i am a software engineer who likes to engineer software".split(" ")
        indexer.addAllToIndices("doc1.txt", tokens)

        val tokensByDocument = indexer.findTokensByDocument("doc1.txt")
        assertEquals(1, tokensByDocument["i"])
        assertEquals(1, tokensByDocument["am"])
        assertEquals(1, tokensByDocument["a"])
        assertEquals(2, tokensByDocument["software"])
        assertEquals(2, tokensByDocument["engineer"])
        assertEquals(1, tokensByDocument["who"])
        assertEquals(1, tokensByDocument["likes"])
        assertEquals(1, tokensByDocument["to"])
    }
}