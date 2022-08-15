package com.yanicksenn.miniretrieval

/**
 * Retrieval result with document and score.
 */
data class Result(
    val document: String,
    val score: Double)