package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.to.DocumentResult
import com.yanicksenn.miniretrieval.to.Document

/**
 * The index for tf-idf.
 */
class TFIDFIndex {
    private val tokenizer = TFIDFTokenizer
    private val documentIndex = StringFrequencyByKey()
    private val tokenIndex = StringFrequencyByKey()

    /**
     * Adds the given document to this index.
     * @param document Document
     */
    fun add(document: Document) {
        val tokens = tokenizer.tokenize(document.text)
        tokens.forEach { token ->
            documentIndex.add(document.id, token)
            tokenIndex.add(token, document.id)
        }
    }

    fun query(rawQuery: String): List<DocumentResult> {
        val queryFrequency = StringFrequency()
        val tokens = tokenizer.tokenize(rawQuery)
        tokens.forEach { queryFrequency.add(it) }

        return TFIDFRSV(documentIndex, tokenIndex, queryFrequency).query()
    }
}