package com.yanicksenn.miniretrieval.language

object LexiconsBuilder {
    fun build(): HashMap<Language, Set<String>> {
        return hashMapOf(
            Language.ENGLISH to EnglishLexiconParser().parse(),
            Language.GERMAN to GermanLexiconParser().parse(),
        )
    }
}