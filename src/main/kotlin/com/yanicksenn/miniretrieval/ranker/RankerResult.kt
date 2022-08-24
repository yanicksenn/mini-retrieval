package com.yanicksenn.miniretrieval.ranker

import com.yanicksenn.miniretrieval.to.DocumentId

/**
 * Retrieval result of a document-id.
 */
data class RankerResult(
    val documentId: DocumentId,
    val score: Double
)