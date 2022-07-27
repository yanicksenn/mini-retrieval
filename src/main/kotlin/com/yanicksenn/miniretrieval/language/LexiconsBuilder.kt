package com.yanicksenn.miniretrieval.language

object LexiconsBuilder {
    fun build(): HashMap<String, Set<String>> {
        return hashMapOf(
            "english" to EnglishLexiconParser().parse(),
            "german" to GermanLexiconParser().parse(),
        )
    }
}