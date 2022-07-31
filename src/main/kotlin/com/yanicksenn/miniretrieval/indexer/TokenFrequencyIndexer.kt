package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.stemmer.IStemmer
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import java.io.File

/**
 * A token frequency indexer builds two indices (tokens
 * by document, documents by token) based on tokens that
 * are retrieved from a document.
 */
class TokenFrequencyIndexer(
    private val tokenizers: Map<Language, ITokenizer>,
    private val stopLists: Map<Language, Set<String>>,
    private val lexicons: Map<Language, Set<String>>,
    private val stemmers: Map<Language, IStemmer>) {

    private val documents = HashMap<String, String>()
    private val tokens = HashMap<String, String>()

    private val tokensByDocumentIndex = HashMap<String, HashMap<String, Int>>()
    private val documentsByTokenIndex = HashMap<String, HashMap<String, Int>>()
    private val languageByDocumentIndex = HashMap<String, HashSet<Language>>()

    /**
     * Returns the tokens paired with the amount of
     * occurrences within a document.
     * @param document Document
     */
    fun findTokensByDocument(document: String): Map<String, Int> {
        return tokensByDocumentIndex[document] ?: emptyMap()
    }

    /**
     * Returns the documents paired with the amount of
     * occurrences with a certain token.
     * @param token Token
     */
    fun findDocumentsByToken(token: String): Map<String, Int> {
        return documentsByTokenIndex[token] ?: emptyMap()
    }

    /**
     * Returns all documents that were considered during
     * the generation of the index.
     */
    fun indexedDocuments(): Set<String> {
        return documents.keys
    }

    /**
     * Returns all tokens that were found during the
     * generation oof the index.
     */
    fun indexedTokens(): Set<String> {
        return tokens.keys
    }

    /**
     * Rokenizes and then adds this file the indices.
     * @param document Document
     */
    fun addDocumentToIndex(document: String, text: String) {
        when (val languageResult = determineLanguage(text)) {
            is LanguageDeterminer.Nothing -> return
            is LanguageDeterminer.Match -> {
                val languages = languageResult.languages
                for (language in languages) {
                    val tokenizer = tokenizers[language]!!
                    val stemmer = stemmers[language]!!
                    val stopList = stopLists[language]!!

                    tokenizer.tokenize(text.lowercase())
                        .map { stemmer.stem(it) }
                        .filterNot { stopList.contains(it) }
                        .forEach { addToIndices(document, it, language) }
                }
            }
        }
    }

    private fun determineLanguage(text: String): LanguageDeterminer.Result {
        val languageDeterminer = LanguageDeterminer(lexicons)
        languageDeterminer.readText(text)
        return languageDeterminer.currentLanguage
    }

    private fun addToIndices(document: String, token: String, language: Language) {
        val tokens = tokensByDocumentIndex.getOrPut(document) { HashMap() }
        tokens[token] = tokens.getOrDefault(token, 0) + 1

        val documents = documentsByTokenIndex.getOrPut(token) { HashMap() }
        documents[document] = documents.getOrDefault(document, 0) + 1

        val languages = languageByDocumentIndex.getOrPut(document) { HashSet() }
        languages.add(language)
    }
}