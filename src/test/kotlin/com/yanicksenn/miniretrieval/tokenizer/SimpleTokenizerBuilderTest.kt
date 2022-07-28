package com.yanicksenn.miniretrieval.tokenizer

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test

class SimpleTokenizerBuilderTest {

    @Test
    fun `ensure simple tokenizer builder works`() {
        assertDoesNotThrow { SimpleTokenizerBuilder.build() }
    }
}