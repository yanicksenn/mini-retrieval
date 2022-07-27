package com.yanicksenn.miniretrieval.stoplist

/**
 * Stop-list containing all tokens that should be tossed
 * from an index.
 */
data class StopList(
    private val tokens: Set<String>) : Set<String> {

    override val size: Int
        get() = tokens.size

    override fun contains(element: String): Boolean {
        return tokens.contains(element)
    }

    override fun containsAll(elements: Collection<String>): Boolean {
        return tokens.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return tokens.isEmpty()
    }

    override fun iterator(): Iterator<String> {
        return tokens.iterator()
    }
}
