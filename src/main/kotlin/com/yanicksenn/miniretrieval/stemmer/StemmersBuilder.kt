package com.yanicksenn.miniretrieval.stemmer

import com.yanicksenn.miniretrieval.language.Language

object StemmersBuilder {
    fun build(): HashMap<Language, IStemmer> {
        return hashMapOf(
            Language.ENGLISH to EnglishStemmer(),
            Language.GERMAN to GermanStemmer()
        )
    }
}