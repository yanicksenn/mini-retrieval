package com.yanicksenn.miniretrieval.indexer

import java.io.File

/**
 * API for an indexer.
 */
interface IIndexer {

    /**
     * Returns the tokens paired with the amount of
     * occurrences within a document.
     * @param document Document
     */
    fun findTokensByDocument(document: Document): Map<String, Int>

    /**
     * Returns the documents paired with the amount of
     * occurrences with a certain token.
     * @param token Token
     */
    fun findDocumentsByToken(token: String): Map<Document, Int>

    /**
     * Returns all documents that were considered during
     * the generation of the index.
     */
    fun indexedDocuments(): Set<Document>

    /**
     * Returns all tokens that were found during the
     * generation oof the index.
     */
    fun indexedTokens(): Set<String>

    /**
     * Reads, tokenizes and then adds this file the
     * indices.
     * @param file File
     * @throws IllegalArgumentException When file does not exist
     * @throws IllegalArgumentException When file is not a file
     */
    fun addFileToIndex(file: File)

    /**
     * Reads, tokenizes and then adds all files
     * recursively to the indices.
     * @param file File
     */
    fun addFilesToIndexRecursively(file: File) {
        file.walk()
            .filter { it.isFile }
            .forEach { addFileToIndex(it) }
    }
}