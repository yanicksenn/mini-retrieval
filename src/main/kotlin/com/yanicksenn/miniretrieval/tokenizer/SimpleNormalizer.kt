package com.yanicksenn.miniretrieval.tokenizer

import java.text.Normalizer

/**
 * Simple implementation of the normalizer API.
 */
class SimpleNormalizer : INormalizer {
    override fun normalize(token: String): String {
        return Normalizer.normalize(token, Normalizer.Form.NFD).replace("\\p{Mn}+".toRegex(), "")
    }
}