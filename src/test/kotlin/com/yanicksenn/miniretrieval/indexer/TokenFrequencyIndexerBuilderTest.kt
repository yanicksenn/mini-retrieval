package com.yanicksenn.miniretrieval.indexer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TokenFrequencyIndexerBuilderTest {

    @Test
    fun `ensure simple indexer builder works`() {
        assertDoesNotThrow { TokenFrequencyIndexerBuilder.build() }
    }
}