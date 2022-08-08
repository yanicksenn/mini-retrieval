package com.yanicksenn.miniretrieval.language

import com.yanicksenn.miniretrieval.to.Token
import java.lang.Integer.max

/**
 * Determines the language of a document by reading its tokens
 * and matching them to lexicons.
 */
class LanguageDeterminer(lexicons: Map<Language, Set<Token>>) {

    private val languageByToken = HashMap<Token, HashSet<Language>>()
    private val languageScore = HashMap<Language, Int>()

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
            if (bestMatches.size > 1)
                return Unsure

            return Match(bestMatches.first())
        }


    /**
     * Returns the percentage of matches with the currently
     * determined language.
     */
     val currentAssurance: Double
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
     * Reads a single token and adjusts the language score to
     * determine what language matches this document.
     * @param token Token
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
     * If multiple languages are matched.
     */
    object Unsure : Result

    /**
     * When a language is matched.
     */
    data class Match(val language: Language) : Result

}