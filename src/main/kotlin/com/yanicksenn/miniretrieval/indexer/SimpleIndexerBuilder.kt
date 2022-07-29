package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.SimpleStemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.TokenizersBuilder

object SimpleIndexerBuilder {
    fun build(): SimpleIndexer {
        return SimpleIndexer(
            TokenizersBuilder.build(),
            StopListsBuilder.build(),
            LexiconsBuilder.build(),
            SimpleStemmersBuilder.build()
        )
    }
}