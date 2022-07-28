package com.yanicksenn.miniretrieval.tokenizer

object SimpleTokenizerBuilder {
    fun build(): SimpleTokenizer {
        return SimpleTokenizer(SimpleNormalizer())
    }
}