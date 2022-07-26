package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.IIndexer
import java.io.File

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File,
    private val indexer: IIndexer) : Runnable {

    override fun run() {
        documentsRoot.walk()
            .filter { it.isFile }
            .forEach { indexer.addFileToIndex(it) }
    }
}