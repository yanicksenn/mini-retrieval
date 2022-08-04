package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FrequencyIndexerTest {

    @Test
    fun `should be empty without indexed elements`() {
        val indexer = FrequencyIndexer<String>()
        assertTrue(indexer.isEmpty())
    }

    @Test
    fun `should have zero size without indexed elements`() {
        val indexer = FrequencyIndexer<String>()
        assertEquals(0, indexer.size)
    }

    @Test
    fun `should contain indexed element`() {
        val indexer = FrequencyIndexer<String>()
        indexer.addToIndex("hello")
        assertTrue(indexer.containsKey("hello"))
    }

    @Test
    fun `should not contain not indexed element`() {
        val indexer = FrequencyIndexer<String>()
        indexer.addToIndex("hello")
        assertFalse(indexer.containsKey("world"))
    }

    @Test
    fun `should have frequency one with indexed element`() {
        val indexer = FrequencyIndexer<String>()
        indexer.addToIndex("hello")
        assertEquals(1, indexer["hello"])
    }

    @Test
    fun `should sum frequency of multiple indexed elements`() {
        val indexer = FrequencyIndexer<String>()
        indexer.addToIndex("hello")
        indexer.addToIndex("hello")
        indexer.addToIndex("hello")
        assertEquals(3, indexer["hello"])
    }

    @Test
    fun `should contain same entries as indexed elements`() {
        val indexer = FrequencyIndexer<String>()
        indexer.addToIndex("hello")
        indexer.addToIndex("hello")
        indexer.addToIndex("world")

        val entries = indexer.toList().toSet()
        assertTrue(entries.contains("hello" to 2))
        assertTrue(entries.contains("world" to 1))
    }

    @Test
    fun `should contain actual keys`() {
        val indexer = FrequencyIndexer<String>()
        indexer.addToIndex("hello")
        indexer.addToIndex("world")

        assertEquals(setOf("hello", "world"), indexer.keys)
    }


    @Test
    fun `should contain actual values`() {
        val indexer = FrequencyIndexer<String>()
        indexer.addToIndex("hello")
        indexer.addToIndex("hello")
        indexer.addToIndex("world")

        assertEquals(setOf(2, 1), indexer.values.toSet())
    }
}