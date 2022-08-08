package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.to.Token

/**
 * A tf-idf index builds contains indices (tokens by
 * document, documents by token) based on tokens that
 * are retrieved from a document.
 */
class TFIDFIndex {
    private val tokensByDocumentIndex = HashMap<Document, StringFrequency>()
    private val documentsByTokenIndex = HashMap<Token, StringFrequency>()

    /**
     * Returns all tokens paired with the amount of
     * occurrences within the given document.
     * @param document Document
     */
    fun findTokensByDocument(document: Document): Map<String, Int> {
        return tokensByDocumentIndex[document] ?: emptyMap()
    }

    /**
     * Returns all documents which contain the given
     * token.
     * @param token Token
     */
    fun findDocumentsByToken(token: Token): Map<String, Int> {
        return documentsByTokenIndex[token] ?: emptyMap()
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
        val tokens = tokensByDocumentIndex.getOrPut(document) { StringFrequency() }
        tokens.add(token)

        val documents = documentsByTokenIndex.getOrPut(token) { StringFrequency() }
        documents.add(document)
    }
}