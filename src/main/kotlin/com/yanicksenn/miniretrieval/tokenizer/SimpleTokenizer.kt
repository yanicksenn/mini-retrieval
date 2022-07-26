package com.yanicksenn.miniretrieval.tokenizer

/**
 * Simple implementation of the tokenizer API.
 */
class SimpleTokenizer : ITokenizer {
    override fun tokenize(text: String): List<String> {
        if (text.isBlank())
            return emptyList()

        val tokens = mutableListOf<String>()
        val currentToken = StringBuilder()

        // tokenize with O(n) by checking each character
        // and appending it to the current token if it's
        // a letter or a digit.
        for (i in text.indices) {
            val c = text[i]
            if (c.isLetterOrDigit()) {
                currentToken.append(c.lowercaseChar())

            } else if (currentToken.isNotEmpty()) {
                tokens.add(currentToken.toString())
                currentToken.clear()
            }
        }

        // Append last token if the buffer is not empty.
        if (currentToken.isNotEmpty())
            tokens.add(currentToken.toString())

        return tokens
    }
}
