package com.yanicksenn.miniretrieval.stoplist

import java.io.InputStream

/**
 * Parser for stop lists.
 */
class StopListParser(private val stopListInputStream: InputStream) {

    companion object {
        /**
         * Regex to match comment lines. Comment lines
         * may start with whitespaces, followed by #,
         * and then followed by anything.
         */
        private val COMMENTS_REGEX = "^\\s*#.*".toRegex()
    }

    fun parse(): Set<String> {
        return stopListInputStream.bufferedReader()
            .lineSequence()
            .filterNot { it.matches(COMMENTS_REGEX) }
            .filterNot { it.isBlank() }
            .map { it.trim() }
            .toHashSet()
    }
}