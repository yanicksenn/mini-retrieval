package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.TokenFrequencyIndexer
import com.yanicksenn.miniretrieval.indexer.TokenFrequencyIndexerBuilder
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
        val stopLists = StopListsBuilder.build()
        val lexicons = LexiconsBuilder.build()
        val stemmers = StemmersBuilder.build()

        val stemmedStopLists = HashMap<Language, HashSet<String>>()
        stopLists.keys.forEach { language ->
            val stemmer = stemmers[language]!!
            val stopList = stopLists[language]!!

            stemmedStopLists[language] = stopList.map { token ->
                stemmer.stem(token)
            }.toHashSet()
        }

        val indexer = TokenFrequencyIndexer(tokenizers, stemmedStopLists, lexicons, stemmers)
        documentsRoot.walk()
            .filter { it.isFile }
            .forEach { indexer.addDocumentToIndex(it.absolutePath, it.readText()) }
    }
}