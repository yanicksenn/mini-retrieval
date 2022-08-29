package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import kotlin.math.log10

/**
 * The index for tf-idf.
 */
class TFIDFIndex(private val tokenizer: ITokenizer) {
    private val _documentIndex = StringFrequencyByKey()
    val documentIndex: Map<String, Map<String, Int>>
        get() = _documentIndex

    private val _tokenIndex = StringFrequencyByKey()
    val tokenIndex: Map<String, Map<String, Int>>
        get() = _tokenIndex

    /**
     * Adds the given document to this index.
     * @param document Document
     */
    fun add(document: Document) {
        val tokens = tokenizer.tokenize(document.text)
        tokens.forEach { token ->
            _documentIndex.add(document.id, token)
            _tokenIndex.add(token, document.id)
        }
    }

    fun idf(token: Token): Double {
        return log10((documentIndex.size + 1.0) / ((tokenIndex[token]?.size ?: 0.0).toDouble() + 1.0))
    }
}