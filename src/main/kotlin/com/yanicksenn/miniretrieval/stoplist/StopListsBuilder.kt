package com.yanicksenn.miniretrieval.stoplist

import com.yanicksenn.miniretrieval.language.Language
import com.yanicksenn.miniretrieval.to.Token

object StopListsBuilder {
    fun build(): HashMap<Language, Set<Token>> {
        return hashMapOf(
            Language.ENGLISH to EnglishStopListParser().parse(),
            Language.GERMAN to GermanStopListParser().parse(),
        )
    }
}