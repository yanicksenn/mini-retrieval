package com.yanicksenn.miniretrieval.ranker.tfidf

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.ranker.IRanker
import com.yanicksenn.miniretrieval.ranker.RankerResult
import com.yanicksenn.miniretrieval.to.Document

/**
 * The ranker using tf-idf based ranking model.
 */
class TFIDFRanker : IRanker {
    private val documentIndex = StringFrequencyByKey()
    private val tokenIndex = StringFrequencyByKey()
    private val tokenizer = TFIDFTokenizer

    override fun index(document: Document) {
        val tokens = tokenizer.tokenize(document.text)
        tokens.forEach { token ->
            documentIndex.add(document.id, token)
            tokenIndex.add(token, document.id)
        }
    }

    override fun query(rawQuery: String): List<RankerResult> {
        val queryFrequency = StringFrequency()
        val tokens = tokenizer.tokenize(rawQuery)
        tokens.forEach { queryFrequency.add(it) }

        return RSV(documentIndex, tokenIndex, queryFrequency).query()
    }
}