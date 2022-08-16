package com.yanicksenn.miniretrieval.ranker

import com.yanicksenn.miniretrieval.IResult
import com.yanicksenn.miniretrieval.to.Document

/**
 * A ranker returns a list of results to a given
 * query.
 */
interface IRanker {

    /**
     * Indexes the given document and its text.
     * @param document Document
     * @param text Text
     */
    fun index(document: Document, text: String)

    /**
     * Queries the indexed documents based on the
     * given raw query and returns a list of results
     * beginning with the most relevant.
     * @param rawQuery Raw query
     */
    fun query(rawQuery: String): List<IResult>
}