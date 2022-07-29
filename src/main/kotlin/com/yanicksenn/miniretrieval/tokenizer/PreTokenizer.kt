package com.yanicksenn.miniretrieval.tokenizer

class PreTokenizer : ITokenizer {
    override fun tokenize(text: String): List<String> {
        return text.split("\\s+".toRegex())
            .map { it.trim() }
            .map { it.replace("[\\d\\W]+", "") }
            .filterNot { it.isBlank() }
    }
}