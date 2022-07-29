package com.yanicksenn.miniretrieval.stemmer

import com.yanicksenn.miniretrieval.stemmer.opennlp.GermanStemmerOpennlp

class SimpleGermanStemmer : IStemmer {
    private val stemmer = GermanStemmerOpennlp()

    override fun stem(token: String): String {
        return stemmer.stem(token)
    }
}