package com.yanicksenn.miniretrieval.indexer

/**
 * Counts the frequencies of the added elements.
 */
class Frequency<T> : Map<T, Int> {
    private val frequencies = HashMap<T, Int>()

    override val entries: Set<Map.Entry<T, Int>>
        get() = frequencies.entries

    override val keys: Set<T>
        get() = frequencies.keys

    override val size: Int
        get() = frequencies.size

    override val values: Collection<Int>
        get() = frequencies.values
    
    /**
     * Adds the given element and if it was already
     * contained then its frequency will be increased.
     * @param element Element
     */
    fun add(element: T) {
        frequencies[element] = frequencies.getOrDefault(element, 0) + 1
    }

    override fun containsKey(key: T): Boolean {
        return frequencies.containsKey(key)
    }

    override fun containsValue(value: Int): Boolean {
        return containsValue(value)
    }

    override fun get(key: T): Int? {
        return frequencies[key]
    }

    override fun isEmpty(): Boolean {
        return frequencies.isEmpty()
    }
}