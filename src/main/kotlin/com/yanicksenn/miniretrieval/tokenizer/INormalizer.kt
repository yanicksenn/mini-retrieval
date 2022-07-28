package com.yanicksenn.miniretrieval.tokenizer

/**
 * API for a normalizer.
 */
interface INormalizer {

    /**
     * Returns a normalized token based on the
     * given token. Normalizing means removing
     * accents and diacritics.
     * @param token Token
     */
    fun normalize(token: String): String
}
