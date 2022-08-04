package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.TokenFrequencyIndexer
import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.IStemmer
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import com.yanicksenn.miniretrieval.tokenizer.WhitespaceTokenizer
import java.io.File

/**
 * TFIDF ranking model.
 */
class TFIDF(private val documentsRoot: File) {

    private val stemmers: HashMap<Language, IStemmer>
    private val stopLists: HashMap<Language, HashSet<Token>>
    private val lexicons: HashMap<Language, HashSet<Token>>
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

        val text = query.lowercase()
        val languageDeterminer = LanguageDeterminer(lexicons)
        val rawTokens = tokenizer.tokenize(text)
        rawTokens.forEach { token ->
            languageDeterminer.readToken(token)
        }

        val queryFrequency = StringFrequency()
        when (val result = languageDeterminer.currentLanguage) {
            is LanguageDeterminer.Nothing ->
                rawTokens.forEach { queryFrequency.add(it) }

            is LanguageDeterminer.Match ->
                rawTokens.stemAndFilter(result.languages.first()).forEach { queryFrequency.add(it) }
        }

        return RSV(documentIndexer, queryFrequency).query()
    }

    private fun addToIndex(file: File) {
        val document = file.absolutePath
        println("\tIndexing document $document ...")

        val text = file.readText().lowercase()
        val languageDeterminer = LanguageDeterminer(lexicons)
        val rawTokens = tokenizer.tokenize(text)
        rawTokens.forEach { token ->
            languageDeterminer.readToken(token)
        }

        when (val result = languageDeterminer.currentLanguage) {
            is LanguageDeterminer.Nothing -> indexWithoutLanguage(rawTokens, document)
            is LanguageDeterminer.Match -> indexWithLanguages(rawTokens, document, result.languages)
        }
    }

    private fun indexWithoutLanguage(rawTokens: List<Token>, document: String) {
        rawTokens.forEach { documentIndexer.add(document, it) }
    }

    private fun indexWithLanguages(rawTokens: List<Token>, document: String, languages: Set<Language>) {
        for (language in languages) {
            rawTokens.stemAndFilter(language)
                .forEach { documentIndexer.add(document, it) }
        }
    }

    private fun List<Token>.stemAndFilter(language: Language): List<Token> {
        val stemmer = stemmers[language] ?: return emptyList()
        val stopList = stopLists[language] ?: return emptyList()

        return map { stemmer.stem(it) }
            .filterNot { stopList.contains(it) }
    }
}