package com.yanicksenn.miniretrieval.tokenizer.opennlp

import com.yanicksenn.miniretrieval.tokenizer.ITokenizer
import opennlp.tools.tokenize.WhitespaceTokenizer

class WhitespaceTokenizerOpennlp : ITokenizer {
    private val tokenizer = WhitespaceTokenizer.INSTANCE

    override fun tokenize(text: String): List<String> {
        return tokenizer.tokenize(text).toList()
    }
}