package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.TokenFrequencyIndexer
import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.WhitespaceTokenizer
import java.io.File

/**
 * Wrapper for the actual business logic.
 */
class Application(
    private val documentsRoot: File) : Runnable {

    override fun run() {
        println("Initializing stemmers ...")
        val stemmers = StemmersBuilder.build()

        println("Initializing stop-lists ...")
        val stopLists = StopListsBuilder.build()

        println("Initializing tokenizer ...")
        val tokenizer = WhitespaceTokenizer()

        println("Initializing lexicons ...")
        val lexicons = LexiconsBuilder.build()

        println("Initializing indexer ...")
        val indexer = TokenFrequencyIndexer()


        println("Building stemmed stop-lists ...")
        val stemmedStopLists = HashMap<Language, HashSet<String>>()
        for ((language, stopList) in stopLists) {
            val stemmer = stemmers[language]!!
            stemmedStopLists[language] = stopList.map { stemmer.stem(it) }.toHashSet()
        }

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
                        val stopList = stemmedStopLists[language] ?: return

                        tokens
                            .map { stemmer.stem(it) }
                            .filterNot { stopList.contains(it) }
                            .forEach { indexer.addToIndices(name, it) }
                    }
                }
            }
    }
}