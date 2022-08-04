package com.yanicksenn.miniretrieval.stoplist

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GermanStopListParserTest {

    @Test
    fun `should not fail while parsing`() {
        assertDoesNotThrow { GermanStopListParser.parse() }
    }

    @Test
    fun `should yield the same result after parsing multiple times`() {
        val first = GermanStopListParser.parse()
        val second = GermanStopListParser.parse()
        assertEquals(first, second)
    }
}