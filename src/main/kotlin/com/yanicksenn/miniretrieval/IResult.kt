package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.to.DocumentId

/**
 * Retrieval result of a document-id.
 */
interface IResult {
    val documentId: DocumentId
}