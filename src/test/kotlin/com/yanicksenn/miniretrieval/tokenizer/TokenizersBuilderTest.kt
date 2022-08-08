package com.yanicksenn.miniretrieval.tokenizer

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test

class TokenizersBuilderTest {

    @Test
    fun `ensure tokenizers builder works`() {
        assertDoesNotThrow { TokenizersBuilder.build() }
    }
}