package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer

/**
 * The index for tf-idf.
 */
class TFIDFIndex(private val tokenizer: ITokenizer) {
    private val documentIndex = StringFrequencyByKey()
    private val tokenIndex = StringFrequencyByKey()

    val documents: Map<String, Map<String, Int>>
        get() = documentIndex

    val tokens: Map<String, Map<String, Int>>
        get() = tokenIndex

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
}