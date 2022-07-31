package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.stemmer.IStemmer

object StemmedStopListsBuilder {
    fun build(stopLists: Map<Language, IStemmer>): HashMap<Language, Set<String>> {
        return hashMapOf(
            Language.ENGLISH to EnglishStopListParser(stopLists[Language.ENGLISH]!!).parse(),
            Language.GERMAN to GermanStopListParser(stopLists[Language.GERMAN]!!).parse(),
        )
    }
}