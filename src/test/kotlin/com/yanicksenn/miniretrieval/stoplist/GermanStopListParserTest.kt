package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.language.GermanLexiconParser
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GermanStopListParserTest {

    @Test
    fun `ensure german stop-list parser can be initialised`() {
        assertDoesNotThrow { GermanLexiconParser }
    }

    @Test
    fun `ensure parsing multiple times works`() {
        val first = GermanLexiconParser.parse()
        val second = GermanLexiconParser.parse()
        assertEquals(first, second)
    }
}