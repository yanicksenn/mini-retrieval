package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.to.Document

/**
 * Retrieval result of a document.
 */
interface IResult {
    val document: Document
}