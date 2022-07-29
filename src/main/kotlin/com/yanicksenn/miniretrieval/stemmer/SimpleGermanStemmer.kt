package com.yanicksenn.miniretrieval.stemmer

import opennlp.tools.stemmer.snowball.SnowballStemmer

class SimpleGermanStemmer : IStemmer {
    private val stemmer = SnowballStemmer(SnowballStemmer.ALGORITHM.GERMAN)

    override fun stem(token: String): String {
        return stemmer.stem(token).toString()
    }
}