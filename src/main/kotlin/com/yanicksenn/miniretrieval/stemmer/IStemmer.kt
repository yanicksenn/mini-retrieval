package com.yanicksenn.miniretrieval.stemmer

import com.yanicksenn.miniretrieval.to.Token

/**
 * API for a stemmer.
 */
interface IStemmer {

    /**
     * Returns the stem of the given token.
     * @param token Token
     */
    fun stem(token: Token): Token
}