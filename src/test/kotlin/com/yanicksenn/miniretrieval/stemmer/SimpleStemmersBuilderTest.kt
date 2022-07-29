package com.yanicksenn.miniretrieval.stemmer

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test

class SimpleStemmersBuilderTest {

    @Test
    fun `ensure simple stemmers builder works`() {
        assertDoesNotThrow { SimpleStemmersBuilder.build() }
    }
}