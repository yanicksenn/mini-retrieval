package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.parser.AnyDocumentParser
import com.yanicksenn.miniretrieval.to.DocumentResult
import com.yanicksenn.miniretrieval.ranker.tfidf.TFIDFRanker
import java.io.File
import kotlin.math.log10
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds

/**
 * Wrapper for the actual business logic.
 */
class Application(private val documentsRoot: File) {
    private val ranker = TFIDFRanker()

    /**
     * Indexes all files within the documents root that
     * have a known file extension.
     */
    fun index(): Application {
        println("Indexing ...")

        val start = System.currentTimeMillis()
        documentsRoot.walk()
            .filter { it.isFile }
            .flatMap { AnyDocumentParser.parse(it) }
            .forEach {
                println("\t${it.id}")
                ranker.index(it)
            }
        val stop = System.currentTimeMillis()

        val duration = (stop - start).milliseconds
        println("Indexing took $duration")
        println()

        return this
    }

    /**
     * Queries the n best results of the given query within
     * the indexed documents.
     * @param query Query
     * @param maxResults Max amount of results
     */
    fun query(query: String, maxResults: Int = 10): Application {
        println("Querying \"$query\" ...")

        val start = System.currentTimeMillis()
        val results = ranker.query(query)
        val bestResults = results.take(min(results.size, maxResults))
        val stop = System.currentTimeMillis()

        bestResults.printResults()

        val duration = (stop - start).milliseconds
        println("Querying took $duration")
        println()

        return this
    }

    private fun List<DocumentResult>.printResults() {
        if (isEmpty()) {
            println("\tNo results found")

        } else {
            val paddingLength = (log10(size.toDouble()) + 1).toInt()
            withIndex().forEach { (i, result) ->
                println("\t${(i + 1).toString().padStart(paddingLength)}. ${result.documentId} (${result.score})")
            }
        }
    }
}