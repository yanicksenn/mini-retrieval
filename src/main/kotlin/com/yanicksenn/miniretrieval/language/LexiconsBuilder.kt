package com.yanicksenn.miniretrieval.language

import com.yanicksenn.miniretrieval.to.Token

object LexiconsBuilder {
    fun build(): HashMap<Language, HashSet<Token>> {
        return hashMapOf(
            Language.ENGLISH to EnglishLexiconParser.parse(),
            Language.GERMAN to GermanLexiconParser.parse(),
        )
    }
}