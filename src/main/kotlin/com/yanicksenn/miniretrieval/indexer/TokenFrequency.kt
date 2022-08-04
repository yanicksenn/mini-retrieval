package com.yanicksenn.miniretrieval.indexer

import com.yanicksenn.miniretrieval.to.Token

/**
 * Counts the frequencies of the added tokens.
 */
class TokenFrequency : Map<Token, Int> {
    private val frequencies = HashMap<Token, Int>()

    override val entries: Set<Map.Entry<Token, Int>>
        get() = frequencies.entries

    override val keys: Set<Token>
        get() = frequencies.keys

    override val size: Int
        get() = frequencies.size

    override val values: Collection<Int>
        get() = frequencies.values

    /**
     * Adds the given token and if it was already
     * contained then its frequency will be increased.
     * @param token Token
     */
    fun add(token: Token) {
        frequencies[token] = frequencies.getOrDefault(token, 0) + 1
    }

    override fun containsKey(key: Token): Boolean {
        return frequencies.containsKey(key)
    }

    override fun containsValue(value: Int): Boolean {
        return containsValue(value)
    }

    override fun get(key: String): Int? {
        return frequencies[key]
    }

    override fun isEmpty(): Boolean {
        return frequencies.isEmpty()
    }
}