package com.yanicksenn.miniretrieval.stoplist

object StopListsBuilder {
    fun build(): HashMap<String, Set<String>> {
        return hashMapOf(
            "english" to EnglishStopListParser().parse(),
            "german" to GermanStopListParser().parse(),
        )
    }
}