package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.language.EnglishLexiconParser
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EnglishStopListParserTest {

    @Test
    fun `ensure english stop-list parser can be initialised`() {
        assertDoesNotThrow { EnglishLexiconParser() }
    }

    @Test
    fun `ensure parsing multiple times works`() {
        val first = EnglishLexiconParser().parse()
        val second = EnglishLexiconParser().parse()
        assertEquals(first, second)
    }
}