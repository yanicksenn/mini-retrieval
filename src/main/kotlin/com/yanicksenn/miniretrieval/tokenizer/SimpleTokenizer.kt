package com.yanicksenn.miniretrieval.tokenizer

import opennlp.tools.tokenize.WhitespaceTokenizer

/**
 * Simple implementation of the tokenizer API.
 */
class SimpleTokenizer : ITokenizer {
    private val tokenizer = WhitespaceTokenizer.INSTANCE

    override fun tokenize(text: String): List<String> {
        return tokenizer.tokenize(text).toList()
    }

}
