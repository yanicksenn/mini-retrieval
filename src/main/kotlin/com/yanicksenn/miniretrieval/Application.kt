package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.adapter.PDFDocumentParser
import com.yanicksenn.miniretrieval.adapter.PPTDocumentParser
 import com.yanicksenn.miniretrieval.adapter.TXTDocumentParser
import com.yanicksenn.miniretrieval.ranker.IRanker
import com.yanicksenn.miniretrieval.to.Document
import java.io.File
import kotlin.math.log10
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds

/**
 * Wrapper for the actual business logic.
 */
class Application(private val documentsRoot: File, private val ranker: IRanker) {

    /**
     * Indexes all files within the documents root that
     * have a known file extension.
     */
    fun index(): Application {
        println("Indexing ...")

        val start = System.currentTimeMillis()
        documentsRoot.walk()
            .filter { it.isFile }
            .flatMap { parse(it) }
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

        if (bestResults.isEmpty()) {
            println("\tNo results found")
        } else {
            val paddingLength = calculatePadding(maxResults)
            bestResults.withIndex().forEach { (i, document) ->
                println("\t${(i + 1).toString().padStart(paddingLength)}. ${document.documentId}")
            }
        }

        val duration = (stop - start).milliseconds
        println("Querying took $duration")
        println()

        return this
    }

    private fun calculatePadding(maxResults: Int) = (log10(maxResults.toDouble()) + 1).toInt()

    private fun parse(file: File): Sequence<Document> {
        return when (file.extension.lowercase()) {
            "pdf" -> PDFDocumentParser.parse(file)
            "pptx" -> PPTDocumentParser.parse(file)
            "txt" -> TXTDocumentParser.parse(file)
            else -> emptySequence()
        }
    }
}