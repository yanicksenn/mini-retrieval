package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.to.Token

/**
 * A token frequency indexer builds two indices (tokens
 * by document, documents by token) based on tokens that
 * are retrieved from a document.
 */
class TokenFrequencyIndexer {
    private val tokensByDocumentIndex = HashMap<Document, HashSet<Token>>()
    private val documentsByTokenIndex = HashMap<Token, HashSet<Document>>()
    private val frequencies = HashMap<Document, TokenFrequency>()

    /**
     * Returns all tokens paired with the amount of
     * occurrences within the given document.
     * @param document Document
     */
    fun findTokensByDocument(document: Document): Set<Token> {
        return tokensByDocumentIndex[document] ?: emptySet()
    }

    /**
     * Returns all documents which contain the given
     * token.
     * @param token Token
     */
    fun findDocumentsByToken(token: Token): Set<Document> {
        return documentsByTokenIndex[token] ?: emptySet()
    }

    /**
     * Returns the frequency of the given token within
     * the given token. If it does not exist then zero
     * is returned.
     * @param document Document
     * @param token Token
     */
    fun findFrequency(document: Document, token: Token): Int {
        return frequencies[document]?.get(token) ?: 0
    }

    /**
     * Returns all documents that were considered during
     * the generation of the index.
     */
    fun indexedDocuments(): Set<String> {
        return tokensByDocumentIndex.keys
    }

    /**
     * Returns all tokens that were found during the
     * generation oof the index.
     */
    fun indexedTokens(): Set<String> {
        return documentsByTokenIndex.keys
    }

    /**
     * Adds the given document-token combination to the
     * indices.
     * @param document Document
     * @param token Token
     */
    fun add(document: Document, token: Token) {
        val tokens = tokensByDocumentIndex.getOrPut(document) { HashSet() }
        tokens.add(token)

        val documents = documentsByTokenIndex.getOrPut(token) { HashSet() }
        documents.add(document)

        val frequency = frequencies.getOrPut(document) { TokenFrequency() }
        frequency.add(token)
    }
}