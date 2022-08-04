package com.yanicksenn.miniretrieval.indexer

/**
 * Counts the frequencies of the added strings.
 */
class StringFrequency : Map<String, Int> {
    private val frequencies = HashMap<String, Int>()

    override val entries: Set<Map.Entry<String, Int>>
        get() = frequencies.entries

    override val keys: Set<String>
        get() = frequencies.keys

    override val size: Int
        get() = frequencies.size

    override val values: Collection<Int>
        get() = frequencies.values

    /**
     * Adds the given string and if it was already
     * contained then its frequency will be increased.
     * @param string String
     */
    fun add(string: String) {
        frequencies[string] = frequencies.getOrDefault(string, 0) + 1
    }

    override fun containsKey(key: String): Boolean {
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