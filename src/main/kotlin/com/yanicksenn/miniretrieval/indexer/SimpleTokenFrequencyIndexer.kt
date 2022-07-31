package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.stemmer.IStemmer
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import java.io.File

/**
 * Simple implementation of the indexer API using term frequency.
 */
class SimpleTokenFrequencyIndexer(
    private val tokenizers: Map<Language, ITokenizer>,
    private val stopLists: Map<Language, Set<String>>,
    private val lexicons: Map<Language, Set<String>>,
    private val stemmers: Map<Language, IStemmer>) : ITokenFrequencyIndexer {

    private val stemmedStopLists = HashMap<Language, HashSet<String>>()

    private val documents = HashMap<Document, Document>()
    private val tokens = HashMap<String, String>()

    private val tokensByDocumentIndex = HashMap<Document, HashMap<String, Int>>()
    private val documentsByTokenIndex = HashMap<String, HashMap<Document, Int>>()
    private val languageByDocumentIndex = HashMap<Document, HashSet<Language>>()

    init {
        stopLists.keys.forEach { language ->
            val stemmer = stemmers[language]!!
            val stopList = stopLists[language]!!

            stemmedStopLists[language] = stopList.map { token ->
                stemmer.stem(token)
            }.toHashSet()
        }
    }

    override fun findTokensByDocument(document: Document): Map<String, Int> {
        return tokensByDocumentIndex[document] ?: emptyMap()
    }

    override fun findDocumentsByToken(token: String): Map<Document, Int> {
        return documentsByTokenIndex[token] ?: emptyMap()
    }

    override fun indexedDocuments(): Set<Document> {
        return documents.keys
    }

    override fun indexedTokens(): Set<String> {
        return tokens.keys
    }

    override fun addFileToIndex(file: File) {
        require(file.exists()) { "${file.absolutePath} does not exist" }
        require(file.isFile) { "${file.absolutePath} is not a file" }

        println(file.absolutePath)

        // O(n) parsing file and tokenize
        val text = file.readText().lowercase()
        when (val languageResult = determineLanguage(text)) {
            is LanguageDeterminer.Nothing -> return
            is LanguageDeterminer.Match -> {
                val languages = languageResult.languages
                for (language in languages) {
                    val tokenizer = tokenizers[language]!!
                    val stemmer = stemmers[language]!!
                    val stopList = stemmedStopLists[language]!!

                    tokenizer.tokenize(text)
                        .map { stemmer.stem(it) }
                        .filterNot { stopList.contains(it) }
                        .flatMap { listOf(it, it.replace("".toRegex(), "")) }
                        .forEach { addToIndices(Document(file.absolutePath), it, language) }
                }
            }
        }
    }

    private fun determineLanguage(text: String): LanguageDeterminer.Result {
        val languageDeterminer = LanguageDeterminer(lexicons)
        languageDeterminer.readText(text)
        return languageDeterminer.currentLanguage
    }

    private fun addToIndices(document: Document, token: String, language: Language) {
        val tokens = tokensByDocumentIndex.getOrPut(document) { HashMap() }
        tokens[token] = tokens.getOrDefault(token, 0) + 1

        val documents = documentsByTokenIndex.getOrPut(token) { HashMap() }
        documents[document] = documents.getOrDefault(document, 0) + 1

        val languages = languageByDocumentIndex.getOrPut(document) { HashSet() }
        languages.add(language)
    }
}