package com.yanicksenn.miniretrieval.language

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test

class EnglishLexiconParserTest {

    @Test
    fun `should not fail while parsing`() {
        assertDoesNotThrow { EnglishLexiconParser.parse() }
    }

    @Test
    fun `should yield the same result after parsing multiple times`() {
        val first = EnglishLexiconParser.parse()
        val second = EnglishLexiconParser.parse()
        kotlin.test.assertEquals(first, second)
    }
}