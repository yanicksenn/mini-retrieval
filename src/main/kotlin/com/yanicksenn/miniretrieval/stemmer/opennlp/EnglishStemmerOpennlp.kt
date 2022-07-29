package com.yanicksenn.miniretrieval.stemmer.opennlp

import com.yanicksenn.miniretrieval.stemmer.IStemmer
import opennlp.tools.stemmer.snowball.SnowballStemmer

class EnglishStemmerOpennlp : IStemmer {
    private val stemmer = SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH)

    override fun stem(token: String): String {
        return stemmer.stem(token).toString()
    }
}