package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.TokenFrequency
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
class TFIDF(private val documentsRoot: File) {

    private val stemmers: HashMap<Language, IStemmer>
    private val stopLists: HashMap<Language, HashSet<String>>
    private val lexicons: HashMap<Language, HashSet<String>>
    private val tokenizer: ITokenizer

    private val documentIndexer: TokenFrequencyIndexer

    init {
        println("Initializing stemmers ...")
        stemmers = StemmersBuilder.build()

        println("Initializing lexicons ...")
        lexicons = LexiconsBuilder.build()

        println("Initializing tokenizer ...")
        tokenizer = WhitespaceTokenizer()

        println("Initializing document indexer ...")
        documentIndexer = TokenFrequencyIndexer()
        
        println("Building stemmed stop-lists ...")
        stopLists = HashMap()
        for ((language, stopList) in StopListsBuilder.build()) {
            val stemmer = stemmers[language]!!
            stopLists[language] = stopList.map { stemmer.stem(it) }.toHashSet()
        }
    }

    /**
     * Rebuilds the document indexer based on the all
     * files within the documents root which have not
     * yet been indexed.
     */
    fun rebuildDocumentIndex(): TFIDF {
        println("Indexing documents ...")
        documentsRoot.walk()
            .filter { it.isFile }
            .filterNot { documentIndexer.indexedDocuments().contains(it.absolutePath) }
            .forEach { addToIndex(it) }
        return this
    }

    /**
     * Queries the relevant documents based on the
     * provided queries.
     * @param query Query
     */
    fun query(query: String): List<RSV.Result> {
        println("Querying documents ...")

        val queryFrequency = TokenFrequency()
        tokenizeStemAndFilter(query)
            .forEach { queryFrequency.add(it) }

        return RSV(documentIndexer, queryFrequency).query()
    }

    private fun addToIndex(file: File) {
        val name = file.absolutePath
        println("\tIndexing document $name ...")

        val text = file.readText()
        tokenizeStemAndFilter(text)
            .forEach { documentIndexer.add(name, it) }
    }

    private fun tokenizeStemAndFilter(text: String): List<String> {
        val lowercaseText = text.lowercase()
        val rawTokens = tokenizer.tokenize(lowercaseText)
        val languageDeterminer = LanguageDeterminer(lexicons)
        rawTokens.forEach { token ->
            languageDeterminer.readToken(token)
        }

        when (val result = languageDeterminer.currentLanguage) {
            is LanguageDeterminer.Nothing -> return emptyList()
            is LanguageDeterminer.Match -> {
                val language = result.language
                val stemmer = stemmers[language] ?: return emptyList()
                val stopList = stopLists[language] ?: return emptyList()

                return rawTokens
                    .map { stemmer.stem(it) }
                    .filterNot { stopList.contains(it) }
            }
        }
    }
}