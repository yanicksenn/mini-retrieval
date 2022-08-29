package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.parser.AnyDocumentParser
import com.yanicksenn.miniretrieval.to.DocumentResult
import java.io.File
import kotlin.math.log10
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds

/**
 * Application taking care indexing and querying with tf-idf.
 */
class TFIDFApplication(private val documentsRoot: File) {
    private val tokenizer = TFIDFTokenizer
    private val index = TFIDFIndex(tokenizer)

    /**
     * Indexes all files within the documents root that
     * have a known file extension.
     */
    fun index(): TFIDFApplication {
        println("Indexing ...")

        val start = System.currentTimeMillis()
        documentsRoot.walk()
            .filter { it.isFile }
            .flatMap { AnyDocumentParser.parse(it) }
            .forEach {
                println("\t${it.id}")
                index.add(it)
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
     * @param rawQuery Raw query
     * @param maxResults Max amount of results
     */
    fun query(rawQuery: String, maxResults: Int = 10): TFIDFApplication {
        println("Querying \"$rawQuery\" ...")

        val start = System.currentTimeMillis()

        val queryFrequency = StringFrequency()
        val tokens = tokenizer.tokenize(rawQuery)
        tokens.forEach { queryFrequency.add(it) }

        val results = TFIDFRSV(index, queryFrequency).query()
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