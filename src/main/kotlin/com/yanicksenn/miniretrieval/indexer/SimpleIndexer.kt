package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import java.io.File

/**
 * Simple implementation of the indexer API using term frequency.
 */
class SimpleIndexer(
    private val tokenizer: ITokenizer,
    private val stopLists: Map<String, Set<String>>,
    private val lexicons: Map<String, Set<String>>) : IIndexer {

    private val documents = HashMap<Document, Document>()
    private val tokens = HashMap<String, String>()

    private val tokensByDocumentIndex = HashMap<Document, HashMap<String, Int>>()
    private val documentsByTokenIndex = HashMap<String, HashMap<Document, Int>>()


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

        val document = getDocumentReference(Document(file))
        val stopList = stopLists["english"] ?: emptySet()

        file.tokenizeFile()
            .filterNot { stopList.contains(it) }
            .map { getTokenReference(it) }
            .forEach { token ->
                addToDocumentIndex(document, token)
                addToInvertedDocumentIndex(document, token)
            }
    }

    private fun getDocumentReference(potentialDocumentReference: Document): Document {
        return documents.getOrPut(potentialDocumentReference) { potentialDocumentReference }
    }

    private fun getTokenReference(potentialTokenReference: String): String {
        return tokens.getOrPut(potentialTokenReference) { potentialTokenReference }
    }

    private fun addToDocumentIndex(document: Document, token: String) {
        val tokens = tokensByDocumentIndex.getOrPut(document) { HashMap() }
        tokens[token] = tokens.getOrDefault(token, 0) + 1
    }

    private fun addToInvertedDocumentIndex(document: Document, token: String) {
        val documents = documentsByTokenIndex.getOrPut(token) { HashMap() }
        documents[document] = documents.getOrDefault(document, 0) + 1
    }

    private fun File.tokenizeFile() = tokenizer.tokenize(readText())
}