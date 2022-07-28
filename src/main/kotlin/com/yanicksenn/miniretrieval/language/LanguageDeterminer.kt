package com.yanicksenn.miniretrieval.language

import java.lang.Integer.max

/**
 * Determines the language of a document by reading its tokens
 * and matching them to lexicons.
 */
class LanguageDeterminer(lexicons: Map<String, Set<String>>) {

    private val languageByToken = HashMap<String, HashSet<String>>()
    private val languageScore = HashMap<String, Int>()

    private var currentMaxScore = 0
    private var tokensRead = 0
    private var tokensMatched = 0

    /**
     * Returns the current determined language.
     */
    val currentLanguage: Result
        get() {
            if (currentMaxScore == 0)
                return Nothing

            val bestMatches = languageScore.filter { it.value == currentMaxScore }.keys.toHashSet()
            return Match(bestMatches)
        }


    /**
     * Returns the percentage of matches with the currently
     * determined language.
     */
     val currentAssurrance: Double
        get() = if (tokensMatched > 0) currentMaxScore / tokensMatched.toDouble() else 0.0

    init {
        // Create inverted index of tokens within lexicons
        for (language in lexicons.keys)
            for (token in lexicons[language]!!)
                languageByToken.getOrPut(token) { HashSet() }.add(language)

        // Initialize score for each language in the lexicons
        lexicons.keys.forEach { languageScore[it] = 0 }
    }

    /**
     * Reads multiple tokens and adjusts the language score to
     * determine what language matches this document.
     */
    fun readTokens(tokens: Iterable<String>) {
        tokens.forEach { readToken(it) }
    }

    /**
     * Reads a single token and adjusts the language score to
     * determine what language matches this document.
     */
    fun readToken(token: String) {
        tokensRead ++

        if (!languageByToken.containsKey(token))
            return

        tokensMatched ++

        val languages = languageByToken[token]!!
        for (language in languages) {
            val oldScore = languageScore[language]!!
            val newScore = oldScore + 1
            languageScore[language] = newScore

            currentMaxScore = max(currentMaxScore, newScore)
        }
    }


    /**
     * Result of a language determination.
     */
    sealed interface Result

    /**
     * No language is matched.
     */
    object Nothing : Result

    /**
     * When at least one language is matched.
     */
    data class Match(val languages: Set<String>) : Result

}