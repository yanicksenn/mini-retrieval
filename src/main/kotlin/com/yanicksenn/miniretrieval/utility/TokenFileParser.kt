package com.yanicksenn.miniretrieval.utility

import com.yanicksenn.miniretrieval.to.Token
import java.io.InputStream

/**
 * Parser for files that contains tokens per line.
 */
open class TokenFileParser(private val tokenFileInputStream: InputStream) {

    companion object {
        /**
         * Regex to match comment lines. Comment lines
         * may start with whitespaces, followed by #,
         * and then followed by anything.
         */
        private val COMMENTS_REGEX = "^\\s*#.*".toRegex()
    }

    private var tokens: HashSet<Token>? = null

    /**
     * Parses the specified token file line by line
     * and removes comments.
     */
    fun parse(): HashSet<Token> {
        if (tokens == null) {
            tokens = tokenFileInputStream.bufferedReader()
                .lineSequence()
                .filterNot { it.matches(COMMENTS_REGEX) }
                .filterNot { it.isBlank() }
                .map { it.trim() }
                .map { manipulate(it) }
                .toHashSet()
        }

        return HashSet(tokens!!)
    }

    /**
     * Manipulates the given token to allow modifications
     * such as stemming.
     * @param token Token
     */
    open fun manipulate(token: Token): Token {
        return token
    }
}