package com.yanicksenn.miniretrieval

import java.io.File

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        TFIDF(documentsRoot).run()
    }
}