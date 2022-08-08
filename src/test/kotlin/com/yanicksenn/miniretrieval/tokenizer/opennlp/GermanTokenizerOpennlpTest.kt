package com.yanicksenn.miniretrieval.tokenizer.opennlp

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GermanTokenizerOpennlpTest {

    @Test
    fun `should initialize without exception`() {
        assertDoesNotThrow { GermanTokenizerOpennlp() }
    }
}