package com.yanicksenn.miniretrieval.tokenizer

/**
 * Simple implementation of the tokenizer API.
 */
class SimpleTokenizer(private val normalizer: INormalizer) : ITokenizer {

    override fun tokenize(text: String): List<String> {
        if (text.isBlank())
            return emptyList()

        return text.split("\\s+")
            .asSequence()
            .map { it.lowercase() }
            .map { normalizer.normalize(it) }
            .map { it.split(" ") }.flatten()
            .map { it.split("/") }.flatten()
            .map { it.replace("\\W+".toRegex(), "") }
            .map { it.trim() }
            .filterNot { it.isBlank() }
            .filterNot { it.matches("[\\d\\W]+".toRegex()) }
            .toList()
    }
}
