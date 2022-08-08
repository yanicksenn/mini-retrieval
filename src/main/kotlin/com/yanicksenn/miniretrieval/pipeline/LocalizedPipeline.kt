package com.yanicksenn.miniretrieval.pipeline

import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.stemmer.StemmersBuilder
import com.yanicksenn.miniretrieval.stoplist.StopListsBuilder
import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.TokenizersBuilder

object LocalizedPipeline {
    private val stemmers = StemmersBuilder.build()
    private val stopLists = StopListsBuilder.build()
    private val tokenizers = TokenizersBuilder.build()

    fun pipeline(language: Language, text: String): List<Token> {
        val stemmer = stemmers[language]!!
        val stopList = stopLists[language]!!
        val tokenizer = tokenizers[language]!!

        return tokenizer.tokenize(text)
            .map { stemmer.stem(it) }
            .filterNot { stopList.contains(it) }
    }
}