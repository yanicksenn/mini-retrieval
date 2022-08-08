package com.yanicksenn.miniretrieval.indexer

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class StringFrequencyByKeyTest {

    @Test
    fun `should be empty without indexed elements`() {
        val indexer = StringFrequencyByKey()
        assertTrue(indexer.isEmpty())
    }

    @Test
    fun `should have zero size without indexed elements`() {
        val indexer = StringFrequencyByKey()
        assertEquals(0, indexer.size)
    }

    @Test
    fun `should contain indexed key`() {
        val indexer = StringFrequencyByKey()
        indexer.add("doc1.txt", "hello")
        assertTrue(indexer.containsKey("doc1.txt"))
    }

    @Test
    fun `should not contain not indexed key`() {
        val indexer = StringFrequencyByKey()
        indexer.add("doc1.txt", "hello")
        assertFalse(indexer.containsKey("doc2.txt"))
    }

    @Test
    fun `should have frequency one with indexed element`() {
        val indexer = StringFrequencyByKey()
        indexer.add("doc1.txt", "hello")
        assertEquals(1, indexer["doc1.txt"]!!["hello"])
    }

    @Test
    fun `should sum frequency of multiple indexed elements`() {
        val indexer = StringFrequencyByKey()
        indexer.add("doc1.txt", "hello")
        indexer.add("doc1.txt", "hello")
        indexer.add("doc1.txt", "hello")
        assertEquals(3, indexer["doc1.txt"]!!["hello"])
    }

    @Test
    fun `should separate frequencies of elements with different keys`() {
        val indexer = StringFrequencyByKey()
        indexer.add("doc1.txt", "hello")
        indexer.add("doc1.txt", "hello")
        indexer.add("doc2.txt", "hello")
        assertEquals(2, indexer["doc1.txt"]!!["hello"])
        assertEquals(1, indexer["doc2.txt"]!!["hello"])
    }
}