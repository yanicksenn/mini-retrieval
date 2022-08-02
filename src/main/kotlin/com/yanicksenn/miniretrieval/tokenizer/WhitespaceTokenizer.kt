package com.yanicksenn.miniretrieval.tokenizer

class WhitespaceTokenizer : ITokenizer {
    private val tokenizer = opennlp.tools.tokenize.WhitespaceTokenizer.INSTANCE

    override fun tokenize(text: String): List<String> {
        return tokenizer.tokenize(text).toList()
    }
}