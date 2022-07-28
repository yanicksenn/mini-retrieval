package com.yanicksenn.miniretrieval.language

import java.io.InputStream

/**
 * Parser for lexicons.
 */
open class LexiconParser(private val lexiconsInputStream: InputStream) {

    companion object {
        /**
         * Regex to match comment lines. Comment lines
         * may start with whitespaces, followed by #,
         * and then followed by anything.
         */
        private val COMMENTS_REGEX = "^\\s*#.*".toRegex()
    }

    private var lexicon: HashSet<String>? = null

    fun parse(): Set<String> {
        if (lexicon == null) {
            lexicon = lexiconsInputStream.bufferedReader()
                .lineSequence()
                .filterNot { it.matches(COMMENTS_REGEX) }
                .filterNot { it.isBlank() }
                .map { it.trim() }
                .toHashSet()
        }

        return lexicon!!
    }
}