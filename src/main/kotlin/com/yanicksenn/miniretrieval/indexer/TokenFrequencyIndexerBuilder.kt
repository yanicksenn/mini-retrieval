package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.language.LexiconsBuilder
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StemmedStopListsBuilder
import com.yanicksenn.miniretrieval.tokenizer.TokenizersBuilder

object TokenFrequencyIndexerBuilder {
    fun build(): TokenFrequencyIndexer {
        val tokenizers = TokenizersBuilder.build()
        val lexicons = LexiconsBuilder.build()
        val stemmers = StemmersBuilder.build()
        val stopLists = StemmedStopListsBuilder.build(stemmers)

        return TokenFrequencyIndexer(
            tokenizers,
            lexicons,
            stemmers,
            stopLists
        )
    }
}