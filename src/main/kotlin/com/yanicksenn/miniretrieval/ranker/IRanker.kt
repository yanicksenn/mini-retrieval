package com.yanicksenn.miniretrieval.ranker

import com.yanicksenn.miniretrieval.IResult
import java.io.File

/**
 * A ranker returns a list of results to a given
 * query.
 */
interface IRanker {

    /**
     * Builds the index that is used for querying.
     */
    fun index(): Sequence<File>

    /**
     * Queries the indexed documents based on the
     * given raw query and returns a list of results
     * beginning with the most relevant.
     * @param rawQuery Raw query
     */
    fun query(rawQuery: String): List<IResult>
}