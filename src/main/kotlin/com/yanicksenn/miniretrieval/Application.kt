package com.yanicksenn.miniretrieval

import java.io.File
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        val tfidf = TFIDF(documentsRoot)
            .rebuildDocumentIndex()

        println("Ready for querying ...")

        while (true) {
            val query = readln()
            val duration = measureTimeMillis {
                val results = tfidf.query(query)
                if (results.isEmpty()) {
                    println("No results found")
                } else {
                    results.forEach { println(it) }
                }
            }.milliseconds

            println("Querying took $duration")
            println()
        }
    }
}