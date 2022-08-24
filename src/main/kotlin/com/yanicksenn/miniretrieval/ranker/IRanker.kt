package com.yanicksenn.miniretrieval.ranker

import com.yanicksenn.miniretrieval.to.Document

/**
 * A ranker returns a list of results to a given
 * query.
 */
interface IRanker {

    /**
     * Adds the given document to the index.
     * @param document Document
     */
    fun index(document: Document)

    /**
     * Queries the indexed documents based on the
     * given raw query and returns a list of results
     * beginning with the most relevant.
     * @param rawQuery Raw query
     */
    fun query(rawQuery: String): List<RankerResult>
}