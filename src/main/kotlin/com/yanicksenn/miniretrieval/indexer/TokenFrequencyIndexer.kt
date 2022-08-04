package com.yanicksenn.miniretrieval.indexer

/**
 * A token frequency indexer builds two indices (tokens
 * by document, documents by token) based on tokens that
 * are retrieved from a document.
 */
class TokenFrequencyIndexer {
    private val tokensByDocumentIndex = HashMap<String, FrequencyIndexer<String>>()
    private val documentsByTokenIndex = HashMap<String, HashSet<String>>()

    /**
     * Returns all tokens paired with the amount of
     * occurrences within the given document.
     * @param document Document
     */
    fun findTokensByDocument(document: String): FrequencyIndexer<String> {
        return tokensByDocumentIndex[document] ?: FrequencyIndexer()
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
     * Adds all given document-tokens combinations to the
     * indices.
     * @param document Document
     * @param tokens Tokens
     */
    fun addAllToIndices(document: String, tokens: List<String>) {
        tokens.forEach { addToIndices(document, it) }
    }

    /**
     * Adds the given document-token combination to the
     * indices.
     * @param document Document
     * @param token Token
     */
    fun addToIndices(document: String, token: String) {
        val tokens = tokensByDocumentIndex.getOrPut(document) { FrequencyIndexer() }
        tokens.addToIndex(token)

        val documents = documentsByTokenIndex.getOrPut(token) { HashSet() }
        documents.add(document)
    }
}