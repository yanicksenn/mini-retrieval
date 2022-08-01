package com.yanicksenn.miniretrieval.stoplist

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class StemmedStopListsBuilderTest {

    @Test
    fun `ensure stop-lists is not empty`() {
        val stopListsByLanguage = StopListsBuilder.build()
        assertTrue(stopListsByLanguage.keys.isNotEmpty(), "stop-list has no languages")
    }

    @Test
    fun `ensure stop-lists languages have no empty stop-lists`() {
        val stopListsByLanguage = StopListsBuilder.build()
        for (stopListByLanguage in stopListsByLanguage) {
            assertTrue(stopListByLanguage.value.isNotEmpty(), "stop-list for language ${stopListByLanguage.key} has no tokens")
        }
    }
}