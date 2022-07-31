package com.yanicksenn.miniretrieval.utility

import java.io.InputStream

/**
 * Parser for files that contains tokens per line.
 */
open class TokenFileParser(private val tokenFileParser: InputStream) {

    companion object {
        /**
         * Regex to match comment lines. Comment lines
         * may start with whitespaces, followed by #,
         * and then followed by anything.
         */
        private val COMMENTS_REGEX = "^\\s*#.*".toRegex()
    }

    private var tokens: HashSet<String>? = null

    open fun parse(): Set<String> {
        if (tokens == null) {
            tokens = tokenFileParser.bufferedReader()
                .lineSequence()
                .filterNot { it.matches(COMMENTS_REGEX) }
                .filterNot { it.isBlank() }
                .map { it.trim() }
                .toHashSet()
        }

        return tokens!!
    }
}