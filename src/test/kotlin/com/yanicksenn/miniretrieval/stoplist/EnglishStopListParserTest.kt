package com.yanicksenn.miniretrieval.stoplist

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EnglishStopListParserTest {

    @Test
    fun `should not fail while parsing`() {
        assertDoesNotThrow { EnglishStopListParser.parse() }
    }

    @Test
    fun `should yield the same result after parsing multiple times`() {
        val first = EnglishStopListParser.parse()
        val second = EnglishStopListParser.parse()
        assertEquals(first, second)
    }
}