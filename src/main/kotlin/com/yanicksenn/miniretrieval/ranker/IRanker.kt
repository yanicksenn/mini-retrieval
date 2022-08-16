package com.yanicksenn.miniretrieval.ranker

import com.yanicksenn.miniretrieval.IResult
import com.yanicksenn.miniretrieval.to.DocumentId

/**
 * A ranker returns a list of results to a given
 * query.
 */
interface IRanker {

    /**
     * Indexes the given document-id and its text.
     * @param documentId Document-Id
     * @param text Text
     */
    fun index(documentId: DocumentId, text: String)

    /**
     * Queries the indexed documents based on the
     * given raw query and returns a list of results
     * beginning with the most relevant.
     * @param rawQuery Raw query
     */
    fun query(rawQuery: String): List<IResult>
}