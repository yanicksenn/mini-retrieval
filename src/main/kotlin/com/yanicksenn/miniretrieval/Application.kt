package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.TokenFrequencyIndexerBuilder
import java.io.File

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        val indexer = TokenFrequencyIndexerBuilder.build()
        documentsRoot.walk()
            .filter { it.isFile }
            .forEach { indexer.addFileToIndex(it) }
    }
}