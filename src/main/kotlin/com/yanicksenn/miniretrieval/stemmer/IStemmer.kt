package com.yanicksenn.miniretrieval.stemmer

/**
 * API for a stemmer.
 */
interface IStemmer {

    /**
     * Returns the stem of the given token.
     * @param token Token
     */
    fun stem(token: String): String
}