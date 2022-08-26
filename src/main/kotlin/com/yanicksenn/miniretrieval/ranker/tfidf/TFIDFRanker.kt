package com.yanicksenn.miniretrieval.ranker.tfidf

import com.yanicksenn.miniretrieval.indexer.StringFrequency
import com.yanicksenn.miniretrieval.indexer.StringFrequencyByKey
import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.language.LanguageDeterminer
import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.ranker.IRanker
import com.yanicksenn.miniretrieval.ranker.RankerResult
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.to.Document
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.DefaultTokenizer
import com.yanicksenn.miniretrieval.tokenizer.TokenizersBuilder

/**
 * The ranker using tf-idf based ranking model.
 */
class TFIDFRanker : IRanker {
    private val tokenizer = TFIDFTokenizer

    private val documentIndex = StringFrequencyByKey()
    private val tokenIndex = StringFrequencyByKey()

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