package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.adapter.PDFDocumentParser
import com.yanicksenn.miniretrieval.adapter.PPTXDocumentParser
 import com.yanicksenn.miniretrieval.adapter.TXTDocumentParser
import com.yanicksenn.miniretrieval.ranker.IRanker
import com.yanicksenn.miniretrieval.to.Document
import java.io.File
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds

/**
 * Wrapper for the actual business logic.
 */
class Application(private val documentsRoot: File, private val ranker: IRanker) {

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

    fun query(query: String): Application {
        println("Querying \"$query\" ...")

        val start = System.currentTimeMillis()
        val results = ranker.query(query)
        val bestResults = results.take(min(results.size, 10))
        val stop = System.currentTimeMillis()

        if (bestResults.isEmpty()) {
            println("\tNo results found")
        } else {
            bestResults.forEach { println("\t${it.documentId}") }
        }

        val duration = (stop - start).milliseconds
        println("Querying took $duration")
        println()

        return this
    }

    private fun parse(file: File): Sequence<Document> {
        return when (file.extension.lowercase()) {
            "pdf" -> PDFDocumentParser.parse(file)
            "pptx" -> PPTXDocumentParser.parse(file)
            "txt" -> TXTDocumentParser.parse(file)
            else -> emptySequence()
        }
    }
}