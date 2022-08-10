package com.yanicksenn.miniretrieval.pipeline

import com.yanicksenn.miniretrieval.to.Token
import com.yanicksenn.miniretrieval.tokenizer.DefaultTokenizer

object PreTokenizationPipeline {
    private val defaultTokenizer = DefaultTokenizer

    fun pipeline(text: String): List<Token> {
        return defaultTokenizer.tokenize(text)
    }
}