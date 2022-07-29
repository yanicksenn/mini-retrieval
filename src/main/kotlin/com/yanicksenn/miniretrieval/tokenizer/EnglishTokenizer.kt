package com.yanicksenn.miniretrieval.tokenizer

import com.yanicksenn.miniretrieval.tokenizer.opennlp.EnglishTokenizerOpenNLP

class EnglishTokenizer : ITokenizer {
    private val tokenizer = EnglishTokenizerOpenNLP()

    override fun tokenize(text: String): List<String> {
        return tokenizer.tokenize(text)
    }
}