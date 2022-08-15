package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.ranker.tfidf.TfIdfRanker
import java.io.File

/**
 * Entry point for application.
 */
fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "documents root path must be provided" }
    require(args[0].isNotBlank()) { "documents root must not be blank" }
    val documentsRoot = File(args[0])

    require(args.size >= 2) { "query must be provided" }
    require(args[1].isNotBlank()) { "query must not be blank" }
    val query = args[1]

    Application(TfIdfRanker(documentsRoot))
        .index()
        .query(query)
}