package com.yanicksenn.miniretrieval.language

object LexiconsBuilder {
    fun build(): HashMap<Language, HashSet<String>> {
        return hashMapOf(
            Language.ENGLISH to EnglishLexiconParser().parse(),
            Language.GERMAN to GermanLexiconParser().parse(),
        )
    }
}