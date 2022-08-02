package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.TokenFrequencyIndexer
import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.IStemmer
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import com.yanicksenn.miniretrieval.tokenizer.WhitespaceTokenizer
import java.io.File

/**
 * TFIDF ranking model.
 */
class TFIDF(private val documentsRoot: File) : Runnable {

    private val stemmers: HashMap<Language, IStemmer>
    private val stopLists: HashMap<Language, HashSet<String>>
    private val lexicons: HashMap<Language, HashSet<String>>
    private val tokenizer: ITokenizer

    private val indexer: TokenFrequencyIndexer

    init {
        println("Initializing ...")
        stemmers = StemmersBuilder.build()
        tokenizer = WhitespaceTokenizer()
        lexicons = LexiconsBuilder.build()
        indexer = TokenFrequencyIndexer()

        println("Building stemmed stop-lists ...")
        stopLists = HashMap()
        for ((language, stopList) in StopListsBuilder.build()) {
            val stemmer = stemmers[language]!!
            stopLists[language] = stopList.map { stemmer.stem(it) }.toHashSet()
        }
    }

    override fun run() {
        println("Indexing documents ...")
        documentsRoot.walk()
            .filter { it.isFile }
            .forEach { file ->
                val name = file.absolutePath
                println("\tIndexing $name ...")

                val text = file.readText()
                val tokens = tokenizer.tokenize(text)
                val languageDeterminer = LanguageDeterminer(lexicons)
                tokens.forEach { token ->
                    languageDeterminer.readToken(token)
                }

                when (val result = languageDeterminer.currentLanguage) {
                    is LanguageDeterminer.Nothing -> return
                    is LanguageDeterminer.Match -> {
                        val language = result.language
                        val stemmer = stemmers[language] ?: return
                        val stopList = stopLists[language] ?: return

                        tokens
                            .map { stemmer.stem(it) }
                            .filterNot { stopList.contains(it) }
                            .forEach { indexer.addToIndices(name, it) }
                    }
                }
            }
    }


}