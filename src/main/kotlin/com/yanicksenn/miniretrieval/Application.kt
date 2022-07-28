package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.SimpleIndexer
import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListParser
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.SimpleTokenizer
import java.io.File
import java.io.InputStream

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        val stopLists = StopListsBuilder.build()
        val lexicons = LexiconsBuilder.build()
        buildIndexer(stopLists, lexicons)
    }

    private fun buildIndexer(stopLists: HashMap<Language, Set<String>>, lexicons: HashMap<Language, Set<String>>) {
        val indexer = SimpleIndexer(SimpleTokenizer(), stopLists, lexicons)
        indexer.addFilesToIndexRecursively(documentsRoot)
    }
}