package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringFrequencyTest {

    @Test
    fun `should be empty without indexed elements`() {
        val indexer = StringFrequency()
        assertTrue(indexer.isEmpty())
    }

    @Test
    fun `should have zero size without indexed elements`() {
        val indexer = StringFrequency()
        assertEquals(0, indexer.size)
    }

    @Test
    fun `should contain indexed element`() {
        val indexer = StringFrequency()
        indexer.add("hello")
        assertTrue(indexer.containsKey("hello"))
    }

    @Test
    fun `should not contain not indexed element`() {
        val indexer = StringFrequency()
        indexer.add("hello")
        assertFalse(indexer.containsKey("world"))
    }

    @Test
    fun `should have frequency one with indexed element`() {
        val indexer = StringFrequency()
        indexer.add("hello")
        assertEquals(1, indexer["hello"])
    }

    @Test
    fun `should sum frequency of multiple indexed elements`() {
        val indexer = StringFrequency()
        indexer.add("hello")
        indexer.add("hello")
        indexer.add("hello")
        assertEquals(3, indexer["hello"])
    }

    @Test
    fun `should contain same entries as indexed elements`() {
        val indexer = StringFrequency()
        indexer.add("hello")
        indexer.add("hello")
        indexer.add("world")

        val entries = indexer.toList().toSet()
        assertTrue(entries.contains("hello" to 2))
        assertTrue(entries.contains("world" to 1))
    }

    @Test
    fun `should contain actual keys`() {
        val indexer = StringFrequency()
        indexer.add("hello")
        indexer.add("world")

        assertEquals(setOf("hello", "world"), indexer.keys)
    }


    @Test
    fun `should contain actual values`() {
        val indexer = StringFrequency()
        indexer.add("hello")
        indexer.add("hello")
        indexer.add("world")

        assertEquals(setOf(2, 1), indexer.values.toSet())
    }

    @Test
    fun `should return zero when not containing element`() {
        val indexer = StringFrequency()
        indexer.add("hello")
        assertEquals(0, indexer["world"])
    }
}