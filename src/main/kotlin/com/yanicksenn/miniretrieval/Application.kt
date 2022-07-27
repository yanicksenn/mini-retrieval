package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.IIndexer
import com.yanicksenn.miniretrieval.stoplist.StopList
import com.yanicksenn.miniretrieval.stoplist.StopListParser
import java.io.File
import java.io.InputStream

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File,
    private val indexer: IIndexer) : Runnable {

    private val stopLists = HashMap<String, StopList>()

    override fun run() {
        parseStopList("english")
        parseStopList("german")

        indexer.addFilesToIndexRecursively(documentsRoot)
    }

    private fun parseStopList(language: String) {
        stopLists[language] = StopListParser(getResourceAsStream("/stopwords/$language.txt")).parse()
        println("Parsed $language stop-list with ${stopLists[language]!!.size} tokens")
    }

    private fun getResourceAsStream(resourceName: String): InputStream {
        return javaClass.getResourceAsStream(resourceName) ?: throw RuntimeException("resource $resourceName does not exist")
    }
}