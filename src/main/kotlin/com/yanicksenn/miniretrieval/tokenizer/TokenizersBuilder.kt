package com.yanicksenn.miniretrieval.tokenizer

import com.yanicksenn.miniretrieval.language.Language

object TokenizersBuilder {
    fun build(): HashMap<Language, ITokenizer> {
        return hashMapOf(
            Language.ENGLISH to EnglishTokenizer(),
            Language.GERMAN to GermanTokenizer()
        )
    }
}