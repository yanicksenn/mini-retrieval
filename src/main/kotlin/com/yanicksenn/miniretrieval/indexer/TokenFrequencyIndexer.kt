package com.yanicksenn.miniretrieval.indexer

/**
 * A token frequency indexer builds two indices (tokens
 * by document, documents by token) based on tokens that
 * are retrieved from a document.
 */
class TokenFrequencyIndexer {
    private val tokensByDocumentIndex = HashMap<String, HashSet<String>>()
    private val documentsByTokenIndex = HashMap<String, HashSet<String>>()
    private val frequencies = HashMap<String, TokenFrequency>()

    /**
     * Returns all tokens paired with the amount of
     * occurrences within the given document.
     * @param document Document
     */
    fun findTokensByDocument(document: String): Set<String> {
        return tokensByDocumentIndex[document] ?: emptySet()
    }

    /**
     * Returns all documents which contain the given
     * token.
     * @param token Token
     */
    fun findDocumentsByToken(token: String): Set<String> {
        return documentsByTokenIndex[token] ?: emptySet()
    }

    /**
     * Returns the frequency of the given token within
     * the given token. If it does not exist then zero
     * is returned.
     * @param document Document
     * @param token Token
     */
    fun findFrequency(document: String, token: String): Int {
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
    fun addToIndices(document: String, token: String) {
        val tokens = tokensByDocumentIndex.getOrPut(document) { HashSet() }
        tokens.add(token)

        val documents = documentsByTokenIndex.getOrPut(token) { HashSet() }
        documents.add(document)

        val frequency = frequencies.getOrPut(document) { TokenFrequency() }
        frequency.add(token)
    }
}