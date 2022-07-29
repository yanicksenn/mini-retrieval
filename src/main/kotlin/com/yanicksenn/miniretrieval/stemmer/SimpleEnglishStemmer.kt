package com.yanicksenn.miniretrieval.stemmer

import com.yanicksenn.miniretrieval.stemmer.opennlp.EnglishStemmerOpennlp

class SimpleEnglishStemmer : IStemmer {
    private val stemmer = EnglishStemmerOpennlp()

    override fun stem(token: String): String {
        return stemmer.stem(token)
    }
}