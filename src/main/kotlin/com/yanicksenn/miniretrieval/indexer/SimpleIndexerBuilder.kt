package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.SimpleNormalizer
import com.yanicksenn.miniretrieval.tokenizer.SimpleTokenizer

object SimpleIndexerBuilder {
    fun build(): SimpleIndexer {
        return SimpleIndexer(
            SimpleTokenizer(SimpleNormalizer()),
            StopListsBuilder.build(),
            LexiconsBuilder.build()
        )
    }
}