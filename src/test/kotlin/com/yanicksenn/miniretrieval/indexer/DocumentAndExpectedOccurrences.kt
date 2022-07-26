package com.yanicksenn.miniretrieval.indexer

data class DocumentAndExpectedOccurrences(
    val document: Document,
    val expectedOccurrences: Int)