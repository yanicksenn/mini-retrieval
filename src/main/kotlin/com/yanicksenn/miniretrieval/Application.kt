package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.TokenFrequencyIndexer
import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
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
        val stopLists = StopListsBuilder.build()

        val stemmedStopLists = HashMap<Language, HashSet<String>>()
        for ((language, stopList) in stopLists) {
            val stemmer = stemmers[language]!!
            stemmedStopLists[language] = stopList.map { stemmer.stem(it) }.toHashSet()
        }

        val indexer = TokenFrequencyIndexer(tokenizers, lexicons, stemmers, stemmedStopLists)
        documentsRoot.walk()
            .filter { it.isFile }
            .forEach { indexer.addDocumentToIndex(it.absolutePath, it.readText()) }
    }
}