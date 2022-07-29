package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.SimpleIndexer
import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.IStemmer
import com.yanicksenn.miniretrieval.stemmer.SimpleStemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.SimpleNormalizer
import com.yanicksenn.miniretrieval.tokenizer.SimpleTokenizer
import java.io.File

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        val normalizer = SimpleNormalizer()
        val stopLists = StopListsBuilder.build()
        val lexicons = LexiconsBuilder.build()
        val stemmers = SimpleStemmersBuilder.build()
        buildIndexer(normalizer, stopLists, lexicons, stemmers)
    }

    private fun buildIndexer(
        normalizer: SimpleNormalizer,
        stopLists: HashMap<Language, Set<String>>,
        lexicons: HashMap<Language, Set<String>>,
        stemmers: HashMap<Language, IStemmer>
    ) {
        val indexer = SimpleIndexer(SimpleTokenizer(normalizer), stopLists, lexicons, stemmers)
        indexer.addFilesToIndexRecursively(documentsRoot)
    }
}