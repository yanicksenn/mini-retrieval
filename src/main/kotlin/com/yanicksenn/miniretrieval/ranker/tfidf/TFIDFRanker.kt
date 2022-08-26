package com.yanicksenn.miniretrieval.ranker.tfidf

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.to.DocumentResult
import com.yanicksenn.miniretrieval.to.Document

/**
 * The ranker using tf-idf based ranking model.
 */
class TFIDFRanker {
    private val tokenizer = TFIDFTokenizer

    private val documentIndex = StringFrequencyByKey()
    private val tokenIndex = StringFrequencyByKey()

    fun index(document: Document) {
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