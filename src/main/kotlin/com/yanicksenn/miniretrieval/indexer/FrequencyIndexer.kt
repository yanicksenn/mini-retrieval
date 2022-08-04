package com.yanicksenn.miniretrieval.indexer

/**
 * Counts the frequencies of the indexed elements.
 */
class FrequencyIndexer<T> : Map<T, Int> {
    private val index = HashMap<T, Int>()

    override val entries: Set<Map.Entry<T, Int>>
        get() = index.entries

    override val keys: Set<T>
        get() = index.keys

    override val size: Int
        get() = index.size

    override val values: Collection<Int>
        get() = index.values


    /**
     * Adds all given elements to the index and if any
     * was already contained then its frequency will be
     * increased.
     * @param element Element
     */
    fun addAllToIndex(elements: List<T>) {
        elements.forEach { addToIndex(it) }
    }

    /**
     * Adds the given element to the index and if it
     * was already contained then its frequency will
     * be increased.
     * @param element Element
     */
    fun addToIndex(element: T) {
        index[element] = index.getOrDefault(element, 0) + 1
    }

    override fun containsKey(key: T): Boolean {
        return index.containsKey(key)
    }

    override fun containsValue(value: Int): Boolean {
        return containsValue(value)
    }

    override fun get(key: T): Int? {
        return index[key]
    }

    override fun isEmpty(): Boolean {
        return index.isEmpty()
    }
}