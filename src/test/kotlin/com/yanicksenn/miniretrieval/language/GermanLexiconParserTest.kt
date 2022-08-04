package com.yanicksenn.miniretrieval.language

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GermanLexiconParserTest {

    @Test
    fun `should not fail while parsing`() {
        assertDoesNotThrow { GermanLexiconParser.parse() }
    }

    @Test
    fun `should yield the same result after parsing multiple times`() {
        val first = GermanLexiconParser.parse()
        val second = GermanLexiconParser.parse()
        kotlin.test.assertEquals(first, second)
    }
}