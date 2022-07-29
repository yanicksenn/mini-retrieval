package com.yanicksenn.miniretrieval.stemmer

import opennlp.tools.stemmer.snowball.SnowballStemmer

class SimpleEnglishStemmer : IStemmer {
    private val stemmer = SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH)

    override fun stem(token: String): String {
        return stemmer.stem(token).toString()
    }
}