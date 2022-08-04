package com.yanicksenn.miniretrieval.stemmer

import com.yanicksenn.miniretrieval.stemmer.opennlp.GermanStemmerOpennlp

object GermanStemmer : IStemmer {
    private val stemmer = GermanStemmerOpennlp()

    override fun stem(token: String): String {
        return stemmer.stem(token)
    }
}