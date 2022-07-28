package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.language.Language

object StopListsBuilder {
    fun build(): HashMap<Language, Set<String>> {
        return hashMapOf(
            Language.ENGLISH to EnglishStopListParser().parse(),
            Language.GERMAN to GermanStopListParser().parse(),
        )
    }
}