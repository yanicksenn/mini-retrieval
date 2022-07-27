package com.yanicksenn.miniretrieval.language

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EnglishLexiconParserTest {

    @Test
    fun `ensure english lexicon parser can be initialised`() {
        assertDoesNotThrow { EnglishLexiconParser() }
    }
}