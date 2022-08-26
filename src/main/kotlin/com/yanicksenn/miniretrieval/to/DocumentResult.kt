package com.yanicksenn.miniretrieval.to

/**
 * Retrieval result of a document-id.
 */
data class DocumentResult(
    val documentId: DocumentId,
    val score: Double
)