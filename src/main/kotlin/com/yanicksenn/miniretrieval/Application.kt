package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.SimpleIndexerBuilder
import java.io.File

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        val indexer = SimpleIndexerBuilder.build()
        indexer.addFilesToIndexRecursively(documentsRoot)
    }
}