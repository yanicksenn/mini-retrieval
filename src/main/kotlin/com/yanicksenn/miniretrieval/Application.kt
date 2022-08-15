package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.ranker.IRanker
import kotlin.time.Duration.Companion.milliseconds

/**
 * Wrapper for the actual business logic.
 */
class Application(private val ranker: IRanker) {

    fun index(): Application {
        println("Indexing ...")

        val start = System.currentTimeMillis()
        ranker.index().forEach { println("\t${it.absolutePath}") }
        val stop = System.currentTimeMillis()

        val duration = (stop - start).milliseconds
        println("Indexing took $duration")
        println()

        return this
    }

    fun query(query: String): Application {
        println("Querying \"$query\" ...")

        val start = System.currentTimeMillis()
        val results = ranker.query(query)
        val stop = System.currentTimeMillis()

        if (results.isEmpty()) {
            println("\tNo results found")
        } else {
            results.forEach { println("\t${it.document.split("/").last()}") }

            val duration = (stop - start).milliseconds
            println("Querying took $duration")
        }

        println()

        return this
    }
}