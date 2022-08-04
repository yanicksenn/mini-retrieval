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

        measureAndPrint {
            tfidf.rebuildDocumentIndex()
        }

        println()

        while (true) {
            val query = readln()
            measureAndPrint {
                tfidf.query(query)
                    .forEach { println("\t$it") }
            }
            println()
        }
    }

    private inline fun measureAndPrint(block: () -> Unit) {
        val duration = measureTimeMillis { block() }.milliseconds
        println("Done after $duration")
    }
}