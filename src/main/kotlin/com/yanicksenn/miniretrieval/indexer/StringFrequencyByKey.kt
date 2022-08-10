package com.yanicksenn.miniretrieval.indexer

/**
 * Counts the frequencies of the added strings grouped by a key.
 */
class StringFrequencyByKey : Map<String, StringFrequency> {
    private val frequencies = HashMap<String, StringFrequency>()

    override val entries: Set<Map.Entry<String, StringFrequency>>
        get() = frequencies.entries

    override val keys: Set<String>
        get() = frequencies.keys

    override val size: Int
        get() = frequencies.size

    override val values: Collection<StringFrequency>
        get() = frequencies.values

    /**
     * Adds the given string by the key and if it was
     * already contained then its frequency will be
     * increased.
     * @param key Key
     * @param string String
     */
    fun add(key: String, string: String) {
        val frequencyByKey = frequencies.getOrPut(key) { StringFrequency() }
        frequencyByKey.add(string)
    }

    override fun containsKey(key: String): Boolean {
        return frequencies.containsKey(key)
    }

    override fun containsValue(value: StringFrequency): Boolean {
        return frequencies.containsValue(value)
    }

    override fun get(key: String): StringFrequency {
        return frequencies[key] ?: StringFrequency()
    }

    override fun isEmpty(): Boolean {
        return frequencies.isEmpty()
    }
}