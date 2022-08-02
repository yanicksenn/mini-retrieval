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
        val stemmers = StemmersBuilder.build()
        val stopLists = StopListsBuilder.build()

        val stemmedStopLists = HashMap<Language, HashSet<String>>()
        for ((language, stopList) in stopLists) {
            val stemmer = stemmers[language]!!
            stemmedStopLists[language] = stopList.map { stemmer.stem(it) }.toHashSet()
        }

        val tokenizer = WhitespaceTokenizer()
        val lexicons = LexiconsBuilder.build()
        val indexer = TokenFrequencyIndexer()

        documentsRoot.walk()
            .filter { it.isFile }
            .forEach { file ->
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
                            .forEach { indexer.addToIndices(file.absolutePath, it) }
                    }
                }
            }
    }
}