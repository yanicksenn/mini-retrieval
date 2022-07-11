package com.yanicksenn.miniretrieval.tokenizer

/**
 * API for a tokenizer.
 */
interface ITokenizer {

    /**
     * Tokenize the given String and return the tokens in
     * the same order as they appear in the text.
     * @param text Text to tokenize
     */
    fun tokenize(text: String): List<String>
}