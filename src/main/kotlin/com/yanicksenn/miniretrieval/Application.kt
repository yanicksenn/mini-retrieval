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

        while (true) {
            val query = readln()
            val duration = measureTimeMillis {
                tfidf.query(query)
                    .forEach { println(it) }
            }.milliseconds

            println("Querying took $duration")
            println()
        }
    }
}