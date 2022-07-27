package com.yanicksenn.miniretrieval.language

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GermanLexiconParserTest {

    @Test
    fun `ensure german lexicon parser can be initialised`() {
        assertDoesNotThrow { GermanLexiconParser() }
    }
}