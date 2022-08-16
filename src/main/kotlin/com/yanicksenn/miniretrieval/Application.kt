package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.ranker.IRanker
import com.yanicksenn.miniretrieval.to.Document
import java.io.File
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
            .forEach {
                println("\t${it.absolutePath}")
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
        val stop = System.currentTimeMillis()

        if (results.isEmpty()) {
            println("\tNo results found")
        } else {
            results.forEach { println("\t${it.documentId.split("/").last()}") }
        }

        val duration = (stop - start).milliseconds
        println("Querying took $duration")
        println()

        return this
    }

    private fun IRanker.index(file: File) {
        index(Document(file.absolutePath, file.readText()))
    }
}