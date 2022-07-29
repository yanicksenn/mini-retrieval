package com.yanicksenn.miniretrieval.tokenizer

import com.yanicksenn.miniretrieval.tokenizer.opennlp.GermanTokenizerOpenNLP

class GermanTokenizer : ITokenizer {
    private val tokenizer = GermanTokenizerOpenNLP()

    override fun tokenize(text: String): List<String> {
        return tokenizer.tokenize(text)
    }
}