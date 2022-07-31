package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.TokenizersBuilder

object TokenFrequencyIndexerBuilder {
    fun build(): TokenFrequencyIndexer {
        return TokenFrequencyIndexer(
            TokenizersBuilder.build(),
            StopListsBuilder.build(),
            LexiconsBuilder.build(),
            StemmersBuilder.build()
        )
    }
}