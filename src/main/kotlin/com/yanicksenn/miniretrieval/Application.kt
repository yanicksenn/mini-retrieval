package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.SimpleTokenFrequencyIndexerBuilder
import java.io.File

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        val indexer = SimpleTokenFrequencyIndexerBuilder.build()
        indexer.addFilesToIndexRecursively(documentsRoot)
    }
}