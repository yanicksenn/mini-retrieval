package com.yanicksenn.miniretrieval.stemmer

import com.yanicksenn.miniretrieval.language.Language

object SimpleStemmersBuilder {
    fun build(): HashMap<Language, IStemmer> {
        return hashMapOf(
            Language.ENGLISH to SimpleEnglishStemmer(),
            Language.GERMAN to SimpleGermanStemmer()
        )
    }
}