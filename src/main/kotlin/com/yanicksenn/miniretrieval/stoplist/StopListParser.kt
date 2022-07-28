package com.yanicksenn.miniretrieval.stoplist

import java.io.InputStream

/**
 * Parser for stop lists.
 */
open class StopListParser(private val stopListInputStream: InputStream) {

    companion object {
        /**
         * Regex to match comment lines. Comment lines
         * may start with whitespaces, followed by #,
         * and then followed by anything.
         */
        private val COMMENTS_REGEX = "^\\s*#.*".toRegex()
    }

    private var stopList: HashSet<String>? = null

    fun parse(): Set<String> {
        if (stopList == null) {
            stopList = stopListInputStream.bufferedReader()
                .lineSequence()
                .filterNot { it.matches(COMMENTS_REGEX) }
                .filterNot { it.isBlank() }
                .map { it.trim() }
                .toHashSet()
        }

        return stopList!!
    }
}