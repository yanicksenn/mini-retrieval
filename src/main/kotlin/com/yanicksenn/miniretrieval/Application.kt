package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.SimpleIndexer
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListParser
import com.yanicksenn.miniretrieval.tokenizer.SimpleTokenizer
import java.io.File
import java.io.InputStream

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        val stopLists = buildStopLists()
        val lexicons = buildLexicons()
        buildIndexer(stopLists, lexicons)
    }

    private fun buildStopLists(): HashMap<String, Set<String>> {
        val stopLists = HashMap<String, Set<String>>()
        stopLists["english"] = parseStopList("english")
        stopLists["german"] = parseStopList("german")
        return stopLists
    }

    private fun buildLexicons(): HashMap<String, Set<String>> {
        return LexiconsBuilder.build()
    }

    private fun buildIndexer(stopLists: HashMap<String, Set<String>>, lexicons: HashMap<String, Set<String>>) {
        val indexer = SimpleIndexer(SimpleTokenizer(), stopLists, lexicons)
        indexer.addFilesToIndexRecursively(documentsRoot)
    }

    private fun parseStopList(language: String): Set<String> {
        val stopList = StopListParser(getResourceAsStream("/stopwords/$language.txt")).parse()
        println("Parsed $language stop-list with ${stopList.size} tokens")
        return stopList
    }

    private fun getResourceAsStream(resourceName: String): InputStream {
        return object {}.javaClass.getResourceAsStream(resourceName) ?: throw RuntimeException("resource $resourceName does not exist")
    }
}