package com.yanicksenn.miniretrieval.tokenizer

/**
 * Simple implementation of the tokenizer API.
 */
class SimpleTokenizer : ITokenizer {

    override fun tokenize(text: String): List<String> {
        if (text.isBlank())
            return emptyList()

        return text.split("\\s+".toRegex()).toList()
    }
}
