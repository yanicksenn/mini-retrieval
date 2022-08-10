package com.yanicksenn.miniretrieval.utility

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SequenceUtilitiesKtTest {

    @Test
    fun `should iterate for each element and continue the sequence`() {
        val sequence = sequenceOf("abc", "DEF", "123")
        val visited = mutableMapOf<String, Int>()

        sequence
            .forEachAnd { visited[it] = visited.getOrDefault(it, 0) + 1 }
            .forEach { visited[it] = visited.getOrDefault(it, 0) + 1 }

        assertEquals(3, visited.size)
        assertEquals(2, visited["abc"])
        assertEquals(2, visited["DEF"])
        assertEquals(2, visited["123"])
    }
}