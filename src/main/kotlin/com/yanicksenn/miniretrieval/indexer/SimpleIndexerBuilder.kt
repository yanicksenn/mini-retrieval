package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.SimpleTokenizerBuilder

object SimpleIndexerBuilder {
    fun build(): SimpleIndexer {
        return SimpleIndexer(
            SimpleTokenizerBuilder.build(),
            StopListsBuilder.build(),
            LexiconsBuilder.build()
        )
    }
}