package com.yanicksenn.miniretrieval

import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.to.DocumentId
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import kotlin.math.log10

/**
 * The index for tf-idf.
 */
class TFIDFIndex(private val tokenizer: ITokenizer) : ITFIDFIndex {
    private val _documentIndex = StringFrequencyByKey()
    private val _tokenIndex = StringFrequencyByKey()
    private val _documentWeight = HashMap<String, Int>()

    override val documentIndex: Map<DocumentId, Map<Token, Int>>
        get() = _documentIndex

    override val tokenIndex: Map<Token, Map<DocumentId, Int>>
        get() = _tokenIndex

    override val documentWeight: Map<DocumentId, Int>
        get() = _documentWeight

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

        val weight = (documentIndex[document.id] ?: emptyMap()).values.sum()
        _documentWeight[document.id] = weight
    }

    fun idf(token: Token): Double {
        return log10((documentIndex.size + 1.0) / ((tokenIndex[token]?.size ?: 0.0).toDouble() + 1.0))
    }
}