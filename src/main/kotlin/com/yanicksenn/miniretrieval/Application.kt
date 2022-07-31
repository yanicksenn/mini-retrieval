package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.TokenFrequencyIndexer
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StemmedStopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.TokenizersBuilder
import java.io.File

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        val tokenizers = TokenizersBuilder.build()
        val lexicons = LexiconsBuilder.build()
        val stemmers = StemmersBuilder.build()
        val stopLists = StemmedStopListsBuilder.build(stemmers)

        val indexer = TokenFrequencyIndexer(tokenizers, lexicons, stemmers, stopLists)
        documentsRoot.walk()
            .filter { it.isFile }
            .forEach { indexer.addDocumentToIndex(it.absolutePath, it.readText()) }
    }
}